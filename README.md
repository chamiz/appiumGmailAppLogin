# appiumGmailAppLogin
Test Gmail App Login function using Appium

# Prerequisites

# Installations
1. Install NodeJS
2. Install Appium using NodeJS
3. Install Android Studio

# Configurations
1. Set ANDROID_HOME system variable
2. set ANDROID_HOME/tools system variable
3. set ANDROID_HOME/emulator system variable

<br />
Install Appium doctor and execute to check paths

# Other
Create Gmail account with

user name : "FOC Solutions Demo
<br />
email  : "foc.solutions.demo@gmail.com"
<br />
password: "foc.solutions"

# Steps
1. Open Android Studio -> Manage Emulators 
2. Create new AVD "Pixel 2" with API version 27
3. Import project into IDE, I used InteliJ IDEA which is my favorite
4. Import dependencies using gradle
5. Execute GmailLoginTest as a Junit Test case
6. Test will automatically start Appium server,Android Emulator and start executing tests
7. DesiredCapabilities of Appium driver defined in "desiredCapabilities.properties" file)

Screenshots will be captured into "Screenshot" directory.
<br />
Video recordings related to sucessful test execution included in "Screen recordings" folder.


