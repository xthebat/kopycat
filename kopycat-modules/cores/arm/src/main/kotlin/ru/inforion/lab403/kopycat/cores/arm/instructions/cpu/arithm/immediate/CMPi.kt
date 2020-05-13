package ru.inforion.lab403.kopycat.cores.arm.instructions.cpu.arithm.immediate

import ru.inforion.lab403.kopycat.cores.arm.AddWithCarry
import ru.inforion.lab403.kopycat.cores.arm.enums.Condition
import ru.inforion.lab403.kopycat.cores.arm.hardware.flags.FlagProcessor
import ru.inforion.lab403.kopycat.cores.arm.instructions.AARMInstruction
import ru.inforion.lab403.kopycat.cores.arm.operands.ARMRegister
import ru.inforion.lab403.kopycat.cores.base.operands.Immediate
import ru.inforion.lab403.kopycat.modules.cores.AARMCore



class CMPi(cpu: AARMCore,
           opcode: Long,
           cond: Condition,
           var setFlags: Boolean,
           var rd: ARMRegister,
           var rn: ARMRegister,
           var imm32: Immediate<AARMCore>,
           size: Int):
        AARMInstruction(cpu, Type.VOID, cond, opcode, rd, rn, imm32, size = size) {

    override val mnem = "CMP$mcnd"

    override fun execute() {
        val (result, carry, overflow) = AddWithCarry(rn.dtyp.bits, rn.value(core), imm32.inv(core), 1)
        FlagProcessor.processArithmFlag(core, result, carry, overflow)
    }
}