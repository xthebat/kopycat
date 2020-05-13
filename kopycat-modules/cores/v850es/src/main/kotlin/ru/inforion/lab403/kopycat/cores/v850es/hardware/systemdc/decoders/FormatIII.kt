package ru.inforion.lab403.kopycat.cores.v850es.hardware.systemdc.decoders

import ru.inforion.lab403.common.extensions.asLong
import ru.inforion.lab403.common.extensions.get
import ru.inforion.lab403.common.extensions.insert
import ru.inforion.lab403.common.extensions.signext
import ru.inforion.lab403.kopycat.cores.base.enums.Datatype
import ru.inforion.lab403.kopycat.cores.v850es.constructor
import ru.inforion.lab403.kopycat.cores.v850es.instructions.AV850ESInstruction
import ru.inforion.lab403.kopycat.cores.v850es.operands.v850esImmediate
import ru.inforion.lab403.kopycat.modules.cores.v850ESCore



class FormatIII(core: v850ESCore, val construct: constructor) : ADecoder<AV850ESInstruction>(core) {

    override fun decode(s: Long): AV850ESInstruction {
        val cond = v850esImmediate(Datatype.BYTE, s[3..0], false)
        val value = s[6..4].insert(s[15..11], 7..3) shl 1
        val disp = v850esImmediate(Datatype.DWORD, signext(value, 9).asLong, true)

        return construct(core, 2, arrayOf(disp, cond))
    }
}