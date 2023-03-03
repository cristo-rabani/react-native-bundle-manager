# react-native-bundle-manager

Allows to change packager host ip
Allows to load ja bundle from remote CDN via URL.
Useful for testing a [Metro](https://github.com/facebook/metro) bundler running remotely.


# Installation

```sh
yarn add react-native-bundle-manager
```

## Android 
in `android/app/src/main/java/<yourAppId>/MainApplication.java`
find line `private final ReactNativeHost mReactNativeHost`
Replace instance of  `new ReactNativeHost(this) {` on `new ReactNativeBundleManagerHost(this) {` 
<img width="841" alt="replace" src="https://user-images.githubusercontent.com/2906283/222664223-fe6d3f9d-35f7-4f43-b7b5-632a8e513add.png">



## Usage

```js
import BundleManager from 'react-native-bundle-manager';

// Set your host ip on which `react-native start` is launched
BundleManager.setPackagerHost('192.168.1.10:8081')

// Or call the `load` method explictly in your own prompt:
BundleManager.load('https://some-remote-url/bundle.js');
```
