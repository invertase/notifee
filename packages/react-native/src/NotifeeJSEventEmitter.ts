/*
 * Copyright (c) 2016-present Invertase Limited
 */

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import EventEmitter from 'react-native/Libraries/vendor/emitter/EventEmitter';
import { EventEmitter as EventEmitterInterface } from 'react-native';

const emitter: EventEmitterInterface = new EventEmitter();
export default emitter;
