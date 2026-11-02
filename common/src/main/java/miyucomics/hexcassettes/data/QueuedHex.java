package miyucomics.hexcassettes.data;

import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.ListIota;

import java.util.ArrayList;
import java.util.List;

import miyucomics.hexcassettes.casting.env.CassetteCastEnv;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

public record QueuedHex(ListIota hex, int delay, int depth) {
    public QueuedHex(ListIota hex, int delay) {
        this(hex, delay, 10);
    }
    public QueuedHex(NbtCompound serialized) {
        this(
            (ListIota)IotaType.TYPED_CODEC.decode(NbtOps.INSTANCE, serialized.get("hex")).getOrThrow().getFirst(),
            serialized.getInt("delay"),
            serialized.getInt("depth")
        );
    }

    public NbtCompound serialize() {
        NbtCompound nbt = new NbtCompound();
        nbt.put("hex", IotaType.TYPED_CODEC.encode(hex, NbtOps.INSTANCE, new NbtCompound()).getOrThrow());
        nbt.putInt("delay", delay);
        nbt.putInt("depth", depth);
        return nbt;
    }

    public void cast(ServerPlayerEntity sp, Iota key) {
        Hand castingHand = !sp.getMainHandStack().isEmpty() && sp.getOffHandStack().isEmpty()
            ? Hand.OFF_HAND : Hand.MAIN_HAND;
        CastingVM vm = CastingVM.empty(new CassetteCastEnv(sp, castingHand, key, depth));

        List<Iota> hexList = new ArrayList<>();
        hex.getList().forEach(hexList::add);

        vm.queueExecuteAndWrapIotas(hexList, sp.getServerWorld());
    }
}
