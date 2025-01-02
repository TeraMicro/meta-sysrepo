# Recipe created by recipetool
SUMMARY = "YANG-based configuration and operational state data store for Unix/Linux applications."
DESCRIPTION = ""
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ef345f161efb68c3836e6f5648b2312f"

SRC_URI = "git://github.com/sysrepo/sysrepo.git;protocol=https;branch=master file://sysrepo"

PV = "2.11.7+git${SRCPV}"
SRCREV = "a6f309eb9601b9ef28e95a74655d7e9eac3dcc7a"

S = "${WORKDIR}/git"

DEPENDS = "libyang protobuf protobuf-c protobuf-c-native libredblack libev libnetconf2"

FILES:${PN} += "/usr/share/yang/* /usr/lib/sysrepo-plugind/*"

inherit cmake pkgconfig python3native python3-dir

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
EXTRA_OECMAKE = " -DCMAKE_INSTALL_PREFIX:PATH=/usr -DCMAKE_BUILD_TYPE:String=Release -DBUILD_EXAMPLES:String=False -DENABLE_TESTS:String=True -DREPOSITORY_LOC:PATH=/etc/sysrepo  -DCALL_TARGET_BINS_DIRECTLY=False -DGEN_LANGUAGE_BINDINGS:String=False "
# -DSYSREPO_UMASK=00007 -DSYSTEMD_UNIT_DIR=/usr/lib/systemd/system -DNACM_RECOVERY_USER=root -DNACM_SRMON_DATA_PERM=000 "

BBCLASSEXTEND = "native nativesdk" 

do_install:append () {
    install -d ${D}/etc/sysrepo/data/notifications
    install -d ${D}/etc/sysrepo/yang
install -o root -g root ${S}/modules/ietf-netconf-notifications@2012-02-06.yang ${D}/etc/sysrepo/yang/ietf-netconf-notifications@2012-02-06.yang
    install -o root -g root ${S}/modules/ietf-netconf-with-defaults@2011-06-01.yang ${D}/etc/sysrepo/yang/ietf-netconf-with-defaults@2011-06-01.yang
    install -o root -g root ${S}/modules/ietf-netconf@2013-09-29.yang ${D}/etc/sysrepo/yang/ietf-netconf@2013-09-29.yang
    install -d ${D}/etc/init.d
    install -m 0775 ${WORKDIR}/sysrepo ${D}/etc/init.d/
    install -d ${D}/usr/lib/sysrepo/plugins
	install -d ${D}/usr/lib/sysrepo/plugins
}

