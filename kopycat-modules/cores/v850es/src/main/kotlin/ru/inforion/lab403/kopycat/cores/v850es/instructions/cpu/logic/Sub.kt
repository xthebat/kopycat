package ru.inforion.lab403.kopycat.cores.v850es.instructions.cpu.logic

import ru.inforion.lab403.kopycat.cores.base.enums.Datatype
import ru.inforion.lab403.kopycat.cores.base.operands.AOperand
import ru.inforion.lab403.kopycat.cores.v850es.hardware.flags.FlagProcessor
import ru.inforion.lab403.kopycat.cores.v850es.instructions.AV850ESInstruction
import ru.inforion.lab403.kopycat.cores.v850es.operands.v850esVariable
import ru.inforion.lab403.kopycat.modules.cores.v850ESCore



class Sub(core: v850ESCore, size: Int, vararg operands: AOperand<v850ESCore>):
        AV850ESInstruction(core, Type.VOID, size, *operands) {
    override val mnem = "sub"

    override val cyChg = true
    override val ovChg = true
    override val sChg = true
    override val zChg = true

    private val result = v850esVariable(Datatype.DWORD)

    // Format I - reg1, reg2
    override fun execute() {
        val a2 = op2.value(core)
        val a1 = op1.value(core)
        val tmp = a2 - a1
        result.value(core, tmp)
        FlagProcessor.processArithmFlag(core, result, op1, op2, true)
        op2.value(core, result)
    }
}