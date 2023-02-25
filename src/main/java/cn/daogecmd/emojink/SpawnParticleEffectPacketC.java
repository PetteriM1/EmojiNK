package cn.daogecmd.emojink;

import cn.nukkit.network.protocol.SpawnParticleEffectPacket;

import java.util.Optional;

public class SpawnParticleEffectPacketC extends SpawnParticleEffectPacket {

    public Optional<String> molangVariablesJson = Optional.empty();

    @Override
    public void encode() {
        this.reset();
        this.putByte((byte) this.dimensionId);
        this.putEntityUniqueId(uniqueEntityId);
        this.putVector3f(this.position);
        this.putString(this.identifier);
        this.putBoolean(this.molangVariablesJson.isPresent());
        this.molangVariablesJson.ifPresent(this::putString);
    }
}
