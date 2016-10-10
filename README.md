# Location 

This a simple Android location app that places a marker on your current location on a Google Map View. You are also able to place a maker on two cities (San Francisco and New York) and the app will place a marker on the map in the respective city you selected

## Getting Started

### Prerequisities

You will need 

* Android Studio 
* Android SDK
* Google Map API Key
* Gradle 
* Java 8
* An Android device or emulator


### Installing

Install Homewbrew by pasting the following command in the terminal 

```
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```

Install Java 8 

```
brew update
brew cask install java
```

Install Gradle 

```
brew install gradle
```

Install Android Studio and SDK Tool 

```
https://developer.android.com/studio/index.html
```

Get a Google Maps API Key 

```
https://developers.google.com/maps/documentation/android/start#get-key
```

Once you have tha above done you can Clone the repo and open in it Android Studio. Before you run the application make sure you place your Google Maps API Key in google_maps_api.xml otherwise the application will not work properly


## Running the app

You'll need an Android device or emulator to be able to run the application. If you have an Android device simply plugin the device to your computer and hit run but make sure your device has developer options and USB debugging enabled To enabled developer options and USB debugging you can follow the link below 

```
http://www.howtogeek.com/129728/how-to-access-the-developer-options-menu-and-enable-usb-debugging-on-android-4.2/
```

Once you have enabled developer and options and USB debugging simply hit run in Android studio to run the app

Otherwise you will need an Android emulator. You can follow the link below for instructions on to install the official Android emulator. Be sure to install an emulator with Google APi to be able to use locations otherwise the app will not work 

```
https://developer.android.com/studio/run/managing-avds.html
```

You can also you use the third party emulator Genymotion. You can follow the link below on how to install Genymotion with Google APi to use locations 

```
https://github.com/codepath/android_guides/wiki/Genymotion-2.0-Emulators-with-Google-Play-support
```

Once you have an emulator you can simple start the emulator and hit run in Android studio to start the app 








