package hyperskill.easy.numberconverter.stage3

import java.math.BigInteger

fun main() {
    problem4()
}

fun problem1() {
    val a = BigInteger(readLine()!!)
    val b = BigInteger(readLine()!!)
    val max = (a + b + (a - b).abs()) / BigInteger.TWO
    println(max)
}

fun problem2() {
    val a = BigInteger(readLine()!!)
    val b = BigInteger(readLine()!!)
    val gcd = a.gcd(b)
    val aMulB = a.multiply(b)
    val lcm = aMulB.divide(gcd) // gcd * lcm = a * b
    println(lcm)
}

fun problem3() {
    val exabyteBytes = BigInteger("9223372036854775808")
    val a = BigInteger(readLine()!!)
    println(a.multiply(exabyteBytes))
}

fun problem4() {
    val a = BigInteger(readLine()!!)
    val b = BigInteger(readLine()!!)
    val sum = a.add(b)
    val percentage1 = a.multiply(BigInteger("100")).divide(sum)
    val percentage2 = b.multiply(BigInteger("100")).divide(sum)
    print("%d%% %d%%".format(percentage1.toInt(), percentage2.toInt()))
}
