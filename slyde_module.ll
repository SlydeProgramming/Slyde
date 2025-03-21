; Generated LLVM IR
; Class: Test
  %hello = type i32
  store i32 0, i32* %hello
  %hi = type i32
  %t0 = add i32 2, 2
  store i32 %t0, i32* %hi
; Constructor
define void @constructor(i32 %y) {
  ret void
}
define void @main() {
  %temp = alloca i32, align 1
  %t1 = add i32 2, 2
  store i32 %t1, i32* %temp
  ret void
}
; Class: test2
  %hi = type i32
  store i32 4, i32* %hi
  %hello = type i32
  store i32 1, i32* %hello
