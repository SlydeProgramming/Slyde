; Generated LLVM IR
declare i32 @puts(i8*); Class: Test
%Test= type {i32, i32}

define void @Test_constructor(%Test* %this, i32 %y) nounwind {
	%hello = getelementptr %Test, %Test* %this, i32 0, i32 0
	store i32 0, i32* %hello
	%hi = getelementptr %Test, %Test* %this, i32 0, i32 1
	%t0 = add i32 2, 2
	store i32 %t0, i32* %hi
	%t1 = load i32, i32* %hi
	%t2 = icmp eq i32 %t1, 4
	br i1 %t2, label %label0, label %label1
	label0:
		store i32 3, i32* %hi
		br label %label2
	label1:
		br label %label2
	label2:
	ret void
}
; Class: Test2
%Test2= type {i32, i32}

define void @Test2_constructor(%Test2* %this) nounwind {
	%hi = getelementptr %Test2, %Test2* %this, i32 0, i32 0
	store i32 4, i32* %hi
	%hello = getelementptr %Test2, %Test2* %this, i32 0, i32 1
	store i32 1, i32* %hello
	ret void
}
define void @main() {
	%temp = alloca i32, align 1
	%t3 = add i32 2, 2
	store i32 %t3, i32* %temp
	br i1 1, label %label3, label %label4
	label3:
		store i32 3, i32* %temp
		br label %label5
	label4:
		br label %label5
	label5:
	ret void
}
