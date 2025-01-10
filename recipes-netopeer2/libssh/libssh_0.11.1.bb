SUMMARY = "Multiplatform C library implementing the SSHv2 and SSHv1 protocol"
HOMEPAGE = "http://www.libssh.org"
SECTION = "libs"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=dabb4958b830e5df11d2b0ed8ea255a0"

DEPENDS = "zlib openssl"

SRC_URI = "git://git.libssh.org/projects/libssh.git;protocol=https;branch=stable-0.11 \
           file://0001-tests-CMakeLists.txt-do-not-search-ssh-sshd-commands.patch \
           file://run-ptest \
          "

SRC_URI:append:toolchain-clang = " file://0001-CompilerChecks.cmake-drop-Wunused-variable-flag.patch"

SRCREV = "854795c654eda518ed6de6c1ebb4e2107fcb2e73"

S = "${WORKDIR}/git"

inherit cmake

PACKAGECONFIG ??= " "
PACKAGECONFIG[gssapi] = "-DWITH_GSSAPI=1, -DWITH_GSSAPI=0, krb5, "
PACKAGECONFIG[tests] = "-DUNIT_TESTING=1, -DUNIT_TESTING=0, cmocka"

#ARM_INSTRUCTION_SET:armv5 = "arm"

EXTRA_OECMAKE = " \
	-DWITH_GCRYPT=0 \
    -DWITH_PCAP=1 \
    -DWITH_SFTP=1 \
    -DWITH_ZLIB=1 \
    -DWITH_EXAMPLES=0 \
    "

do_compile:prepend () {
    if [ ${PTEST_ENABLED} = "1" ]; then
        sed -i -e 's|${B}|${PTEST_PATH}|g' ${B}/config.h
        sed -i -e 's|${S}|${PTEST_PATH}|g' ${B}/config.h
    fi
}

do_install_ptest () {
    install -d ${D}${PTEST_PATH}/tests
    cp -f ${B}/tests/unittests/torture_* ${D}${PTEST_PATH}/tests/
    install -d ${D}${PTEST_PATH}/tests/unittests
    cp -f ${S}/tests/unittests/hello*.sh ${D}${PTEST_PATH}/tests/unittests/
}

BBCLASSEXTEND = "native nativesdk"
