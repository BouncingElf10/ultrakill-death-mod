package net.bouncingelf10.ultrakilldeath.camera;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector2d;

public class FreeCamEntity extends Entity {
    private Vec3d velocity;
    private final float lockedYaw;
    private final float lockedPitch;
    private final boolean shouldApplyGravity;

    public FreeCamEntity(World world, Vec3d pos, Vec3d velocity, float pitch, float yaw) {
        super(EntityType.SNOWBALL, world);
        this.noClip = false;
        this.velocity = velocity;
        this.lockedYaw = yaw;
        this.lockedPitch = pitch;
        this.shouldApplyGravity = velocity.y > 0.01;

        this.setPos(pos.x, pos.y, pos.z);

        this.setYaw(yaw);
        this.prevYaw = yaw;
        this.setPitch(pitch);
        this.prevPitch = pitch;
    }

    @Override
    public void tick() {
        Vector2d normalizedDirection = new Vector2d(this.velocity.x, this.velocity.z).normalize();
        Vec3d normalizedVelocity = velocity.normalize();
        Vec3d movementDirection = new Vec3d(normalizedDirection.x, normalizedVelocity.y, normalizedDirection.y).multiply(0.05);

        if (shouldApplyGravity) {
            this.move(MovementType.SELF, movementDirection);
        } else {
            this.move(MovementType.SELF, movementDirection.multiply(1, 0.8, 1));
        }

        this.updatePosition(this.getX(), this.getY(), this.getZ());

        this.velocity = this.velocity.multiply(0.98f, 1.0f, 0.98f);
        if (shouldApplyGravity) {
            this.velocity = this.velocity.add(0, -0.005, 0);
        }

        this.setYaw(lockedYaw);
        this.prevYaw = lockedYaw;
        this.setPitch(lockedPitch);
        this.prevPitch = lockedPitch;

        this.setVelocity(Vec3d.ZERO);
        this.setNoGravity(true);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {}

    @Override
    protected void readCustomDataFromNbt(net.minecraft.nbt.NbtCompound nbt) {}

    @Override
    protected void writeCustomDataToNbt(net.minecraft.nbt.NbtCompound nbt) {}

    @Override
    public boolean shouldRender(double distance) {
        return false;
    }

    @Override
    public boolean isInvisible() {
        return true;
    }

    @Override
    public boolean isSpectator() {
        return false;
    }
}