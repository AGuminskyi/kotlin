/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package test.collections

import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException
import java.util.Collections
import kotlin.test.*


class ArraysJVMTest {

    @Suppress("HasPlatformType", "UNCHECKED_CAST")
    fun <T> platformNull() = Collections.singletonList(null as T).first()

    @Test
    fun contentEquals() {
        assertTrue(platformNull<IntArray>() contentEquals null)
        assertTrue(null contentEquals platformNull<LongArray>())

        assertFalse(platformNull<Array<String>>() contentEquals emptyArray<String>())
        assertFalse(arrayOf("a", "b") contentEquals platformNull<Array<String>>())

        assertFalse(platformNull<UShortArray>() contentEquals ushortArrayOf())
        assertFalse(ubyteArrayOf() contentEquals platformNull<UByteArray>())
    }

    @Test
    fun contentHashCode() {
        assertEquals(0, platformNull<Array<Int>>().contentHashCode())
        assertEquals(0, platformNull<CharArray>().contentHashCode())
        assertEquals(0, platformNull<ShortArray>().contentHashCode())
        assertEquals(0, platformNull<BooleanArray>().contentHashCode())
        assertEquals(0, platformNull<UByteArray>().contentHashCode())
        assertEquals(0, platformNull<UIntArray>().contentHashCode())
    }

    @Test
    fun contentToString() {
        assertEquals("null", platformNull<Array<String>>().contentToString())
        assertEquals("null", platformNull<CharArray>().contentToString())
        assertEquals("null", platformNull<DoubleArray>().contentToString())
        assertEquals("null", platformNull<FloatArray>().contentToString())
        assertEquals("null", platformNull<ULongArray>().contentToString())
    }

    @Test
    fun contentDeepEquals() {
        assertFalse(platformNull<Array<String>>() contentDeepEquals emptyArray<String>())
        assertFalse(arrayOf("a", "b") contentDeepEquals platformNull<Array<String>>())
    }

    @Test
    fun contentDeepHashCode() {
        assertEquals(0, platformNull<Array<Int>>().contentDeepHashCode())
    }

    @Test
    fun contentDeepToString() {
        assertEquals("null", platformNull<Array<String>>().contentDeepToString())
    }

    @Test
    fun reverseRangeInPlace() {

        fun <TArray, T> doTest(
            build: Iterable<Int>.() -> TArray,
            reverse: TArray.(fromIndex: Int, toIndex: Int) -> Unit,
            snapshot: TArray.() -> List<T>
        ) {
            val arrays = (0..7).map { n -> n to (0 until n).build() }
            for ((size, array) in arrays) {
                for (fromIndex in 0 until size) {
                    for (toIndex in fromIndex until size) {
                        val original = array.snapshot().toMutableList()
                        array.reverse(fromIndex, toIndex)
                        val reversed = array.snapshot()
                        assertEquals(original.apply { subList(fromIndex, toIndex).reverse() }, reversed)
                    }
                }

                assertFailsWith<IndexOutOfBoundsException> { array.reverse(-1, size) }
                assertFailsWith<IndexOutOfBoundsException> { array.reverse(0, size + 1) }
                assertFailsWith<IllegalArgumentException> { array.reverse(0, -1) }
            }
        }

        doTest(build = { map {it}.toIntArray() },               reverse = { from, to -> reverse(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toLong()}.toLongArray() },     reverse = { from, to -> reverse(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toByte()}.toByteArray() },     reverse = { from, to -> reverse(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toShort()}.toShortArray() },   reverse = { from, to -> reverse(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toFloat()}.toFloatArray() },   reverse = { from, to -> reverse(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toDouble()}.toDoubleArray() }, reverse = { from, to -> reverse(from, to) }, snapshot = { toList() })
        doTest(build = { map {'a' + it}.toCharArray() },        reverse = { from, to -> reverse(from, to) }, snapshot = { toList() })
        doTest(build = { map {it % 2 == 0}.toBooleanArray() },  reverse = { from, to -> reverse(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toString()}.toTypedArray() },  reverse = { from, to -> reverse(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toString()}.toTypedArray() as Array<out String> },  reverse = { from, to -> reverse(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toUInt()}.toUIntArray() },     reverse = { from, to -> reverse(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toULong()}.toULongArray() },   reverse = { from, to -> reverse(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toUByte()}.toUByteArray() },   reverse = { from, to -> reverse(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toUShort()}.toUShortArray() }, reverse = { from, to -> reverse(from, to) }, snapshot = { toList() })
    }

    @Test
    fun sortDescendingRangeInPlace() {

        fun <TArray, T : Comparable<T>> doTest(
            build: Iterable<Int>.() -> TArray,
            sortDescending: TArray.(fromIndex: Int, toIndex: Int) -> Unit,
            snapshot: TArray.() -> List<T>
        ) {
            val arrays = (0..7).map { n -> n to (0 until n).build() }
            for ((size, array) in arrays) {
                for (fromIndex in 0 until size) {
                    for (toIndex in fromIndex until size) {
                        val original = array.snapshot().toMutableList()
                        array.sortDescending(fromIndex, toIndex)
                        val reversed = array.snapshot()
                        assertEquals(original.apply { subList(fromIndex, toIndex).sortDescending() }, reversed)
                    }
                }

                assertFailsWith<IndexOutOfBoundsException> { array.sortDescending(-1, size) }
                assertFailsWith<IndexOutOfBoundsException> { array.sortDescending(0, size + 1) }
                assertFailsWith<IllegalArgumentException> { array.sortDescending(0, -1) }
            }
        }

        doTest(build = { map {it}.toIntArray() }, sortDescending = { from, to -> sortDescending(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toLong()}.toLongArray() }, sortDescending = { from, to -> sortDescending(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toByte()}.toByteArray() }, sortDescending = { from, to -> sortDescending(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toShort()}.toShortArray() }, sortDescending = { from, to -> sortDescending(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toFloat()}.toFloatArray() }, sortDescending = { from, to -> sortDescending(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toDouble()}.toDoubleArray() }, sortDescending = { from, to -> sortDescending(from, to) }, snapshot = { toList() })
        doTest(build = { map {'a' + it}.toCharArray() }, sortDescending = { from, to -> sortDescending(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toString()}.toTypedArray() }, sortDescending = { from, to -> sortDescending(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toString()}.toTypedArray() as Array<out String> }, sortDescending = { from, to -> sortDescending(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toUInt()}.toUIntArray() }, sortDescending = { from, to -> sortDescending(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toULong()}.toULongArray() }, sortDescending = { from, to -> sortDescending(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toUByte()}.toUByteArray() }, sortDescending = { from, to -> sortDescending(from, to) }, snapshot = { toList() })
        doTest(build = { map {it.toUShort()}.toUShortArray() }, sortDescending = { from, to -> sortDescending(from, to) }, snapshot = { toList() })
    }
}