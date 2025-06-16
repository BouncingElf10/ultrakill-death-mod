package net.bouncingelf10.ultrakilldeath.camera;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static net.bouncingelf10.ultrakilldeath.ULTRAKILLDeath.LOGGER;

public class FreeCamEntity extends Entity {
    private Vec3d velocity;
    private final float lockedYaw;
    private final float lockedPitch;

    public FreeCamEntity(World world, Vec3d pos, Vec3d velocity, float pitch, float yaw) {
        super(EntityType.MARKER, world);
        this.noClip = true;
        this.velocity = velocity;
        this.lockedYaw = yaw;
        this.lockedPitch = pitch;

        this.setPos(pos.x, pos.y, pos.z);

        this.setYaw(yaw);
        this.prevYaw = yaw;
        this.setPitch(pitch);
        this.prevPitch = pitch;
    }

    @Override
    public void tick() {
        Vec3d newPos = this.getPos().add(velocity.normalize().multiply(0.05f));
        this.updatePosition(newPos.x, newPos.y, newPos.z);
        this.velocity = this.velocity.multiply(0.999f).subtract(0.0f, 0.05f, 0.0f);

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
        return true;
    }
}