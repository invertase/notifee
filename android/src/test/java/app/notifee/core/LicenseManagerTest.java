package app.notifee.core;

import android.os.Build;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LicenseManagerTest {

  @Test
  public void testInvalidLicenseInDebug() {
    LicenseManager licenseManager = getLicenseManager(
      true,
      LicenseManager.LocalVerificationStatus.NO_LICENSE,
      LicenseManager.RemoteVerificationStatus.PENDING,
      28);
    assertFalse("Invalid license valid in debug", licenseManager.isLicenseInvalidInstance());
  }

  @Test
  public void testInvalidLicenseInRelease() {
    LicenseManager licenseManager = getLicenseManager(
      false,
      LicenseManager.LocalVerificationStatus.NO_LICENSE,
      LicenseManager.RemoteVerificationStatus.PENDING,
      28);
    assertTrue("Invalid license not valid in release", licenseManager.isLicenseInvalidInstance());
  }

  @Test
  public void testRemoteLicenseFailure() {
    LicenseManager licenseManager = getLicenseManager(
      false,
      LicenseManager.LocalVerificationStatus.OK,
      LicenseManager.RemoteVerificationStatus.BAD_REQUEST_TOKEN,
      Build.VERSION_CODES.P);
    assertTrue("Remote license failure detected on Android > API23", licenseManager.isLicenseInvalidInstance());
  }

  @Test
  public void testLocalLicenseFailure() {
    LicenseManager licenseManager = getLicenseManager(
      false,
      LicenseManager.LocalVerificationStatus.INVALID_LICENSE,
      LicenseManager.RemoteVerificationStatus.PENDING,
      Build.VERSION_CODES.P);
    assertTrue("Local license failure detected on Android > API20", licenseManager.isLicenseInvalidInstance());
  }

  @Test
  public void testLocalLicenseFailurePreLollipop() {
    LicenseManager licenseManager = getLicenseManager(
      false,
      LicenseManager.LocalVerificationStatus.INVALID_LICENSE,
      LicenseManager.RemoteVerificationStatus.PENDING,
      Build.VERSION_CODES.KITKAT_WATCH);
    assertFalse("Local license failure ignored on Android <= API20", licenseManager.isLicenseInvalidInstance());
  }

  public TestLicenseManager getLicenseManager(boolean debug, int local, int remote, int sdk) {
    TestLicenseManager licenseManager = new TestLicenseManager();
    licenseManager.setDebuggable(debug);
    licenseManager.setLocalStatus(local);
    licenseManager.setRemoteStatus(remote);
    licenseManager.setBuildVersion(sdk);
    return licenseManager;
  }

  private static class TestLicenseManager extends LicenseManager {
    private static boolean debuggable = false;
    private static int localStatus = -1;
    private static int remoteStatus = -1;
    private static int buildSdk = 28; // pretend we are Android API28 / P normally

    @Override
    int getLocalStatus() {
      // delegate to superclass unless specifically set for testing
      if (localStatus == -1) {
        return super.getLocalStatus();
      }
      return localStatus;
    }

    public void setLocalStatus(int status) {
      localStatus = status;
    }

    @Override
    int getRemoteStatus() {
      // delegate to superclass unless specifically set for testing
      if (remoteStatus == -1) {
        return super.getRemoteStatus();
      }
      return remoteStatus;
    }

    public void setRemoteStatus(int status) {
      remoteStatus = status;
    }

    @Override
    int getBuildVersion() {
      return buildSdk;
    }

    public void setBuildVersion(int version) {
      buildSdk = version;
    }

    @Override
    protected boolean isDebug() {
      return debuggable;
    }

    @Override
    protected boolean isLicenseInvalidInstance() {
      return super.isLicenseInvalidInstance();
    }

    public void setDebuggable(boolean debug) {
      if (debuggable) {
        debuggable = debug;
      } else {
        debuggable = debug;
      }
    }
  }
}
