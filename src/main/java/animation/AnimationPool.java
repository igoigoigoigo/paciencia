package animation;

import java.util.LinkedList;
import java.util.List;

public class AnimationPool {
    private final List<Animation> animations = new LinkedList<>();

    public void add(Animation animation) {
        animations.add(animation);
    }

    public void update() {
        animations.removeIf(Animation::isOver);
        animations.forEach(Animation::update);
    }
}
