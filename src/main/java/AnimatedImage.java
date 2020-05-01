import javafx.scene.image.Image;

class AnimatedImage {
    // assumes animation loops,
    //  each image displays for equal time
    Image[] frames;
    double duration;

    Image getFrame(double time) {
        int index = (int) ((time % (frames.length * duration)) / duration);
        return frames[index];
    }
}

