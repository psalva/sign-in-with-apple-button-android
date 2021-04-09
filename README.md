# Sign In with Apple Button for Android

A library for adding [Sign In With Apple](https://developer.apple.com/sign-in-with-apple/) to Android apps.

## How it works

This library includes a `SignInWithAppleButton` class. You can style the button according to Apple's [Human Interface Guidelines](https://developer.apple.com/design/human-interface-guidelines/sign-in-with-apple/overview/).

![Apple HIG themed button in black with default corner radius](docs/hig-button-black.png) ![Apple HIG themed button in white with rounder corners](docs/hig-button-white.png) ![Apple HIG themed button in outlined white with even rounder corners](docs/hig-button-white-outline.png)

You can add this button to your login screen. When tapped, the button presents Apple's OAuth login flow in a web view. After the user signs in, your callback will receive an authorization code. You can then pass the authorization code to your backend's third party login endpoint.

![OAuth flow diagram](docs/flow-diagram.png)

## Do I need this?

You will find this library useful if both of these statements are true:

1. Your service has both an Android app and an iOS app.
2. Your apps include third-party login, like signing in with Google, Facebook, or Twitter.

In June 2019, Apple announced Sign In with Apple, another third-party login provider. They also announced that if an iOS app offers any third-party login options, [it will be an App Store requirement](https://developer.apple.com/news/?id=06032019j) to offer Sign In with Apple. This rule would go into effect "later this year" (2019). That is, if you don't add the feature, at some point you won't be able to ship updates to your iOS app.

Obviously Apple does not control Android. But if you have to add a login method to your iOS app, your users will need it on your Android app too. If it isn't supported, your users won't be able to log in if they switch to Android.

We built this library to make it as painless as possible to add Sign In with Apple to your Android app.

## Instructions

### Service setup

First, follow Apple's instructions to set up Sign In with Apple [in your iOS app](https://help.apple.com/developer-account/#/devde676e696) and [for a web service](https://help.apple.com/developer-account/#/dev1c0e25352). It is the web service setup that you'll use from Android, but you need both.

> More setup is necessary for backend operations, but the above is all you need to use this library. For more detail, you can read Aaron Parecki's walkthrough, [What the Heck is Sign In with Apple?](https://developer.okta.com/blog/2019/06/04/what-the-heck-is-sign-in-with-apple)

You should have created:

- An App ID
    - including the Sign In with Apple capability
- A Service ID
    - using the App ID as its primary
    - mapped to a domain you control
        - which Apple has verified
    - including at least one Return URL

From this setup, you will need two OAuth arguments to use this library:

- A client ID, which you entered as the Identifier field of the Service ID.
- A redirect URI, which you entered as the Return URL.

> We recommend you use an `https://` address for your redirect URI. If you use an `http://` address, you may need to include a security configuration to allow cleartext traffic. Although this library _should_ intercept the redirect request, you should regard this as a less secure option. If it's necessary, see the [Network security configuration documentation](https://developer.android.com/training/articles/security-config#CleartextTrafficPermitted) for instructions on setting up a security configuration. Add that file to your Manifest's `<application>` tag using the attribute `android:android:networkSecurityConfig`.

### Installation

Include as a dependency using Gradle:

```groovy
repositories {
     jcenter()
}

dependencies {
    implementation 'com.github.psalva:signIn-with-apple:$latest_version'
}
```

### Configuration

Add a `SignInWithAppleButton` to your login screen's layout.

Configure the button's appearance properties in layout XML:

- `style`: Specify a style, `"@style/SignInWithAppleButton.Black"` (default), `"@style/SignInWithAppleButton.White"`, or `"@style/SignInWithAppleButton.WhiteOutline"`.
- `sign_in_with_apple_button_textType`: Specify an enum value, `"signInWithApple"` (default) or `"continueWithApple"`.
- `sign_in_with_apple_button_cornerRadius`: Specify a dimension, like `"4dp"` (default), `"0dp"`, `"8px"`, etc.

> These options are based on the style options from Apple's [Human Interface Guidelines](https://developer.apple.com/design/human-interface-guidelines/sign-in-with-apple/overview/).

At runtime, create an instance of `SignInWithAppleConfiguration`, supplying these values:

- `clientId`: Use the client ID value from service setup.
- `redirectUri`: Use the redirect URI value from service setup.
- `scope`: Select a OpenID scopes "NAME ,EMAIL or ALL".
- `responseType`: Select a type of response "CODE ,ID_TOKEN or ALL".

Configure the button with a `FragmentManager` to present the login interface, the service you created above, and a callback to receive the success/failure/cancel result.


Contributions are welcome. Please see the [Contributing guidelines](CONTRIBUTING.md).

This project has adopted a [code of conduct](CODE_OF_CONDUCT.md) defined by the [Contributor Covenant](http://contributor-covenant.org), the same used by the [Swift language](https://swift.org) and countless other open source software teams.

## Disclaimer

The Apple logo belongs to Apple. It's included in this library because it's specified in Apple's [Human Interface Guidelines](https://developer.apple.com/design/human-interface-guidelines/sign-in-with-apple/overview/). We're using it in good faith according to its intended purpose. As a consumer of this library, please read the HIG and avoid misusing Apple's intellectual property.

