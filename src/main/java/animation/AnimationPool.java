package animation;

import java.util.ArrayList;
import java.util.List;

public class AnimationPool {
    private List<Animation> animations;

    public AnimationPool() {
        this.animations = new ArrayList<>();
    }

    public void update(double elapsed) {
        for (int i = 0; i < animations.size(); i++) {
            this.animations.get(i).update(elapsed);

            if (animations.get(i).ended())
                this.animations.remove(animations.get(i));
        }
    }

    public void add(Animation animation) {
        this.animations.add(animation);
    }
}

