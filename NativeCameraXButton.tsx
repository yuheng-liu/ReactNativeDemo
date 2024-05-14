import React from 'react';
import { NativeModules, Button } from 'react-native';

const { CameraModule } = NativeModules;

const NewModuleButton = () => {
    const onPress = async () => {
        console.log('Starting Native CameraX Module');
        try {
            const imageUri = await CameraModule.startCameraActivity();
            console.log(`Received CameraX image Uri: ${imageUri}`);
        } catch (e) {
            console.error(e);
        }
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