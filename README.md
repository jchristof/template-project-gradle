#Kotlin on BrickPi3 + RPi3b

The objective of this project is to:
- Provide an example of Kotlin running on the BrickPi3 board attached to a RPi 3
- Provide a better set of setup instructions for this hardware combo

I have not tested this with an EV3 or other boards, but the code may work for this
hardware without many modifications.

###Changes to the original ev3dev-lang-java template project
- Modified the project build.gradle to include support for Kotlin and Coroutines
- Modified build.grade for source compatibility with Java *
- Update gradle to latest version
- Added a Kotlin example using two NXT motors (KotlinRobot.kt)
- Pointed the manifest to build and package the Kotlin example

For chat help, visit https://gitter.im/ev3dev/chat

ev3dev git: https://github.com/ev3dev

ev3dev java: https://github.com/ev3dev-lang-java

###Get the Debian Stretch ev3dev os

Image here:
https://www.ev3dev.org/docs/getting-started/

###Flash os image to sd card

In the boot partition, open the config.txt file. Modify the contents per the instructions. 
Eject the sd card partitions using the safe eject.
Insert sd card into the Pi + Brick Pi and power on.

SSH from the PC to the Pi:
```
ssh robot@ev3dev.local
```
Provide the password when prompted (the default is 'maker')
Confirm the Pi responds with a greeting and login confirmation

Confirm that the BrickPi hardware and firmware are detected:
```
dmesg | grep brick
```

In the response look for the presence of BrickPi in the response:
```
[ 6.596894] brickpi3 spi0.1: Address: 1
[ 6.604392] brickpi3 spi0.1: Mfg: Dexter Industries
[ 6.616824] brickpi3 spi0.1: Board: BrickPi3
[ 6.641495] brickpi3 spi0.1: HW: 3.2.1
[ 6.684273] brickpi3 spi0.1: FW: 1.4.3
[ 6.696068] brickpi3 spi0.1: ID: DE91878850533357312E3120FF11293D
```

Also,  confirm the presence of a file in /sys/class/lego-port
Also, (on Pi) cat /boot/flash/config.txt file should be present and contain the config changes made in the earlier step

If either of these are unavailable, most likely the config.txt edit was not successful - go back to that step and start over

###Install Java on Debian Stretch 

Stretch does not come installed with a JVM. Install Java to run Java apps.
https://github.com/ev3dev-lang-java/installer

```
sudo apt-get update
sudo apt-get upgrade
sudo apt-get dist-upgrade
sudo reboot
```

Run the following command to remotely fetch and install Java onto the Pi. Run the installer.sh help command and view the options.
```
cd /home/robot
wget -N https://raw.githubusercontent.com/ev3dev-lang-java/installer/master/installer.sh
chmod +x installer.sh
sudo ./installer.sh help
sudo ./installer.sh
```

```
sudo ./installer.sh java will install java
```

Confirm that java was installed correctly: 
```
robot@ev3dev:~$ java -version
openjdk version "11.0.6" 2020-01-14
OpenJDK Runtime Environment (build 11.0.6+10-post-Debian-1bpo91)
OpenJDK Server VM (build 11.0.6+10-post-Debian-1bpo91, mixed mode, sharing)
```

###Compile and run a Kotlin app on the Pi


Get the Java code template project here:
https://github.com/ev3dev-lang-java/template-project-gradle

Open or import the project into Intellij or another IDE
Modify the config.gradle file's host address to allow the IDE to remotely install and run your code:

```
remotes {
    ev3dev {
        host = '<Pi address>'' i.e. 192.168.x.x
        user = 'robot'
        password = 'maker'
    }
}
```

In the IDE's terminal, execute the gradle command to test it's connection to the Pi

```
./gradlew testConnection
```

Connection is confirmed if the task executes without an error. Additionally, it may print the contents of the Pi's /home/robot directory

The template project's MANIFEST.INF file specifies which program will be compile and sent to the Pi to run. The default may look like this:

```
Manifest-Version: 1.0
Implementation-Title: EV3Dev-lang-java
Implementation-Version: 2.7.0-SNAPSHOT
Implementation-Vendor: Juan Antonio Breña Moral
Main-Class: example.KotlinRobotKt
```

Change the Main-Class line to point to the program to run. Example:

```
Main-Class: example.KotlinRobotKt
```

To run the Main-Class program, execute the following gradle tasks in the IDE's terminal:

```
./gradlew build
./gradlew deploy
./gradlew remoteRun
```

Note any errors and fix them along the way.
build - compiles the code
deploy - sends the code to the Pi
remoteRun - executes the code on the Pi

Check the contents of the Pi's /home/robot directory. The jar file for the app that was just executed should exist there now.

Helpful command line commands
ev3dev-sysinfo
sudo update-brickpi3-fw