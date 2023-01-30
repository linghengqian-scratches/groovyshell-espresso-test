- Server for github issue and graalvm slack
    - https://github.com/oracle/graal/issues/5522
    - https://github.com/oracle/graal/issues/5850
    - https://github.com/linghengqian/graalvm-trace-metadata-smoketest/issues/1

- Execute the following command on an Ubuntu 22.04 instance to get unit test results.

```shell
sudo apt install unzip zip curl sed -y
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 22.3.1.r17-grl
sdk use java 22.3.1.r17-grl
sdk install gradle
gu install espresso native-image
sudo apt-get install build-essential libz-dev zlib1g-dev -y

git clone git@github.com:linghengqian/groovyshell-espresso-test.git
cd ./groovyshell-espresso-test/

./gradlew clean -Pagent test
./gradlew metadataCopy --task test
./gradlew clean nativeTest
```

- Error Log in `./gradlew clean -Pagent test` as follows.
```shell
java.lang.AssertionError
	at com.lingh.SimpleGroovyShellTest.testGroovyShellInEspresso(SimpleGroovyShellTest.java:37)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)


SimpleGroovyShellTest > testGroovyShellInEspresso() FAILED
    java.lang.AssertionError at SimpleGroovyShellTest.java:37
1 test completed, 1 failed
> Task :test FAILED
FAILURE: Build failed with an exception.
* What went wrong:
Execution failed for task ':test'.
> There were failing tests. See the report at: file:///home/linghengqian/TwinklingLiftWorks/git/public/groovyshell-espresso-test/build/reports/tests/test/index.html
* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.
* Get more help at https://help.gradle.org
BUILD FAILED in 3s

```