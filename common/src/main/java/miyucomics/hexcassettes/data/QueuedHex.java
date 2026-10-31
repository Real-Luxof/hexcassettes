package miyucomics.hexcassettes.data;

import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import miyucomics.hexcassettes.casting.env.CassetteCastEnv;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

public record QueuedHex(NbtCompound hex, int delay, int depth) {
    public QueuedHex(NbtCompound hex, int delay) {
        this(hex, delay, 10);
    }
    public QueuedHex(NbtCompound serialized) {
        this(serialized.getCompound("hex"), serialized.getInt("delay"), serialized.getInt("depth"));
    }

    public NbtCompound serialize() {
        NbtCompound nbt = new NbtCompound();
        nbt.put("hex", hex);
        nbt.putInt("delay", delay);
        nbt.putInt("depth", depth);
        return nbt;
    }

    public void cast(ServerPlayerEntity sp, NbtCompound key) {
        Hand castingHand = !sp.getMainHandStack().isEmpty() && sp.getOffHandStack().isEmpty()
            ? Hand.OFF_HAND : Hand.MAIN_HAND;
        CastingVM vm = CastingVM.empty(new CassetteCastEnv(sp, castingHand, key, depth));
        Iota hexIota = IotaType.deserialize(hex, sp.getServerWorld());

        // What happens if it's not a list? Can it ever not be a list?
        // I think it should crash if someone screws ABI that bad, honestly. Less mysterious that
        // way.
        if (!(hexIota instanceof ListIota hexListIota)) return;

        List<Iota> hexList = new ArrayList<>();
        hexListIota.getList().forEach(hexList::add);

        vm.queueExecuteAndWrapIotas(hexList, sp.getServerWorld());
    }
}
