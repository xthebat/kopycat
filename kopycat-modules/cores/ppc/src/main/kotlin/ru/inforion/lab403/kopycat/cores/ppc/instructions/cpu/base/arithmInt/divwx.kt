package ru.inforion.lab403.kopycat.cores.ppc.instructions.cpu.base.arithmInt

import ru.inforion.lab403.kopycat.cores.base.enums.Datatype
import ru.inforion.lab403.kopycat.cores.base.operands.AOperand
import ru.inforion.lab403.kopycat.cores.ppc.flags.FlagProcessor
import ru.inforion.lab403.kopycat.cores.ppc.instructions.APPCInstruction
import ru.inforion.lab403.kopycat.cores.ppc.operands.PPCVariable
import ru.inforion.lab403.kopycat.modules.cores.PPCCore



//Divide word
class divwx(core: PPCCore, val overflow: Boolean, val record: Boolean, vararg operands: AOperand<PPCCore>):
        APPCInstruction(core, Type.VOID, *operands) {
    override val mnem = "divw${if (overflow) "o" else ""}${if (record) "." else ""}"

    private val result = PPCVariable(Datatype.DWORD)

    override fun execute() {
        val opA = op2.ssext(core)
        val opB = op3.ssext(core)

        val inv = (opB == 0L) || ((op2.value(core) == 0x8000_0000L) && (opB == -1L))
        if (!inv) {
            result.value(core, opA / opB)

            op1.value(core, result)

            if (record)
                FlagProcessor.processCR0(core, result)
        }
        if (overflow)
            FlagProcessor.processOverflowDiv(core, inv)
    }
}