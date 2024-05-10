import React from 'react';
import { NativeModules, Button } from 'react-native';

const { CameraModule } = NativeModules;

const NewModuleButton = () => {
    const onPress = () => {
        console.log('Starting Native CameraX Module');
        CameraModule.startCameraActivity(`testName`, `testLocation`);
    };

    return (
        <Button
            title="Click to start Native CameraX Module"
            color="#841584"
            onPress={onPress}
        />
    );
};

export default NewModuleButton;