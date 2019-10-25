const PriorityQueue = require('tinyqueue');

const FRAME_DURATION = 1000 / 60;
const IDLE_CALLBACK_FRAME_DEADLINE_MS = 1;

/**
 * Implements RN's native timing functionality but in nodejs for performance
 * @link https://github.com/facebook/react-native/blob/master/React/Modules/RCTTiming.m
 * @link https://github.com/facebook/react-native/blob/master/Libraries/Core/Timers/JSTimers.js
 * @link https://github.com/facebook/react-native/blob/master/ReactAndroid/src/main/java/com/facebook/react/modules/core/Timing.java *
 */
class Timing {
  constructor() {
    this._timers = {};
    this._pQueue = null;
    this._nTiming = null;
    this._jsTiming = null;
    this._fTimeout = null;
    this._stopped = false;
  }

  /* ------------
   *   PRIVATE
   * ---------- */

  /**
   *
   * @private
   */
  _reset() {
    this._timers = {};
    this._jsTiming = null;

    if (this._nTiming) {
      this._nTiming.createTimer = () => {};
      this._nTiming.deleteTimer = () => {};
      this._nTiming.setSendIdleEvents = () => {};
      this._nTiming = null;
    }

    clearTimeout(this._fTimeout);
    this._fTimeout = null;
    this._pQueue = new PriorityQueue([], (a, b) => a.targetTime - b.targetTime);
  }

  /**
   * Process all queued items for the current frame time incl. callIdleCallbacks
   * @private
   */
  _doFrame() {
    if (this._stopped) {
      return;
    }
    const frameTime = Date.now();

    const timersToCall = [];
    while (this._pQueue.length && this._pQueue.peek().targetTime < frameTime) {
      const { id } = this._pQueue.pop();
      if (this._timers[id]) {
        const { repeat, duration } = this._timers[id];
        timersToCall.push(id);
        if (repeat) {
          this._pQueue.push({ id, targetTime: frameTime + duration });
        } else {
          delete this._timers[id];
        }
      }
    }

    if (timersToCall.length) {
      this._jsTiming.callTimers(timersToCall);
    }

    // requestIdleCallback
    let nextFrameDelay = FRAME_DURATION - (Date.now() - frameTime);
    if (nextFrameDelay >= IDLE_CALLBACK_FRAME_DEADLINE_MS) {
      this._jsTiming.callIdleCallbacks(frameTime);
    }

    // queue the next frame
    nextFrameDelay = FRAME_DURATION - (Date.now() - frameTime);
    this._fTimeout = setTimeout(
      this._doFrame.bind(this),
      nextFrameDelay <= 0 ? 0 : nextFrameDelay - 1,
    );
  }

  /* ------------
   *   PUBLIC
   * ---------- */

  /**
   * Start processing timers at a set frame rate of 60fps
   *
   * @param nativeTiming NativeModules.Timing from RN
   * @param jsTiming react-native/Libraries/Core/Timers/JSTimers
   */
  start(nativeTiming, jsTiming) {
    this._reset();
    this._stopped = false;
    this._jsTiming = jsTiming;
    this._nTiming = nativeTiming;

    // force always sending idle events - prevents hanging
    nativeTiming.setSendIdleEvents(true);

    // override native timing fn's
    this._nTiming.createTimer = this.createTimer.bind(this);
    this._nTiming.deleteTimer = this.deleteTimer.bind(this);
    this._nTiming.setSendIdleEvents = this.setSendIdleEvents.bind(this);

    // manually run first frame
    this._doFrame();
  }

  /**
   * Stop and reset - for the purpose of post test cleanup
   */
  stop() {
    this._stopped = true;
    this._reset();
  }

  /**
   * Used by jsTiming - react-native/Libraries/Core/Timers/JSTimers
   * @param id
   * @param duration
   * @param scheduleTime
   * @param repeat
   */
  createTimer(id, duration, scheduleTime, repeat = false) {
    this._timers[id] = {
      duration,
      repeat,
    };

    this._pQueue.push({ id, targetTime: scheduleTime + duration });
  }

  /**
   * Used by jsTiming - react-native/Libraries/Core/Timers/JSTimers
   * @param id
   */
  deleteTimer(id) {
    // only a lazy delete
    // any timers already in queue will self remove when targetTime reached
    delete this._timers[id];
  }

  /**
   * Used by jsTiming - react-native/Libraries/Core/Timers/JSTimers
   */
  setSendIdleEvents() {
    // do nothing for now - we force this always to be true as part of 'start'
    // this._sendIdleEvents = enabled;
  }
}

module.exports = new Timing();
