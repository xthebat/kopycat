package ru.inforion.lab403.kopycat.cores.arm.hardware.systemdc.arm.media

import ru.inforion.lab403.common.extensions.asInt
import ru.inforion.lab403.common.extensions.find
import ru.inforion.lab403.common.extensions.get
import ru.inforion.lab403.kopycat.cores.arm.UInt
import ru.inforion.lab403.kopycat.cores.arm.enums.Condition
import ru.inforion.lab403.kopycat.cores.arm.exceptions.ARMHardwareException.Unpredictable
import ru.inforion.lab403.kopycat.cores.arm.hardware.systemdc.decoders.ADecoder
import ru.inforion.lab403.kopycat.cores.arm.instructions.AARMInstruction
import ru.inforion.lab403.kopycat.cores.arm.hardware.registers.GPRBank
import ru.inforion.lab403.kopycat.cores.arm.operands.ARMRegister
import ru.inforion.lab403.kopycat.modules.cores.AARMCore



class BitFieldOppDecoder(
        cpu: AARMCore,
        val constructor: (
                cpu: AARMCore,
                opcode: Long,
                cond: Condition,
                rd: ARMRegister,
                ra: ARMRegister,
                msBit: Long,
                lsBit: Long) -> AARMInstruction) : ADecoder<AARMInstruction>(cpu) {
    override fun decode(data: Long): AARMInstruction {
        val cond = find<Condition> { it.opcode == data[31..28].asInt } ?: Condition.AL
        val rd = GPRBank.Operand(data[15..12].asInt)
        val rn = GPRBank.Operand(data[3..0].asInt)
        val msBit = UInt(data[20..16], 32)
        val lsBit = UInt(data[11..7], 32)

        if(rd.reg == core.cpu.regs.pc.reg) throw Unpredictable

        return constructor(core, data, cond, rd, rn, msBit, lsBit)
    }
}