package app.notifee.core;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

class LicenseChecker {
  private static LicenseChecker mInstance = null;

  private byte[] mSalt = null;

  private String mPublicKey = null;

  static void initialize(String publicSigningKey) {
    synchronized (LicenseChecker.class) {
      if (mInstance == null) {
        mInstance = new LicenseChecker();
        mInstance.mPublicKey = publicSigningKey;
        mInstance.mSalt = new byte[]{
          -79, -117, -82, -103, 109, -95, -69, -32, -76, 127, -100, -56, -18, -84, 61, -16, 119, 19,
          -81, 105
        };
      }
    }
  }

  static LicenseChecker getInstance() {
    return mInstance;
  }

  private Jwt<Header, Claims> verifyToken(String token) {
    Jwt<Header, Claims> jwt = Jwts.parser().setSigningKey(mPublicKey).parseClaimsJwt(token);
    return jwt;
  }

  private String buildJwt() {
    // TODO
    return "";
  }
}
