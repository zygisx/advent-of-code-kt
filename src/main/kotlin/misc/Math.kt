package misc

import java.math.BigInteger

object Math {

    fun gcd(a: Int, b: Int): Int {
        if (b == 0) return a
        return gcd(b, a % b)
    }

    fun gcd(a: BigInteger, b: BigInteger): BigInteger {
        if (b == BigInteger.ZERO) return a
        return gcd(b, a % b)
    }

    fun lcm(a: BigInteger, b: BigInteger): BigInteger {
        return a.multiply(b).divide(gcd(a, b))
    }
}