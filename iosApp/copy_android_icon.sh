#!/bin/sh

# Script to copy the Android app icon to the iOS app icon directory
# This script should be run from the project root directory

# Create the destination directory if it doesn't exist
mkdir -p iosApp/iosApp/Assets.xcassets/AppIcon.appiconset

# Copy the Android icon to the iOS app icon directory
cp composeApp/src/androidMain/res/mipmap-hdpi/ic_launcher_playstore.png iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/app-icon-1024.png

echo "Android icon copied to iOS app icon directory"