# Android MapLibre SDK / MapTiler Cloud examples

A live Android project/app that demonstrates how to use MapBox SDK for Android with MapTiler cloud.

## Getting started

1. Create [MapTiler cloud](https://www.maptiler.com/cloud/) account.
1. [Obtain the api key](https://cloud.maptiler.com/account/keys).
1. Set the key for MapTiler in the application build.gradle file (`/app/build.gradle`) - put your key into `mapTilerKey` resource value

    ```gradle
    android {
        ...
        defaultConfig {
            ...
            resValue "string", "mapTilerKey", "placeholder"
        }
        ...
    }
    ```
