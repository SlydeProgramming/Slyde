; Generated LLVM IR
declare i32 @puts(i8*)
define void @print(i8** %output){
	%loadedOutput = load i8*, i8** %output
	call i32 @puts(i8* %loadedOutput)
	ret void
}



; Class: Test
%Test= type {i32, i32, i8*}

define void @Test_constructor(%Test* %this) nounwind {
	%hello = getelementptr %Test, %Test* %this, i32 0, i32 0
	store i32 0, i32* %hello
	%hi = getelementptr %Test, %Test* %this, i32 0, i32 1
	%t0 = add i32 2, 2
	store i32 %t0, i32* %hi
	%hope = getelementptr %Test, %Test* %this, i32 0, i32 2
	%t1 = alloca [13 x i8], align 1
	%strVar0 = getelementptr [13 x i8], [13 x i8]* %t1, i32 0, i32 0
	store i8 72, i8* %strVar0
	%strVar1 = getelementptr i8, i8* %strVar0, i32 1
	store i8 101, i8* %strVar1
	%strVar2 = getelementptr i8, i8* %strVar0, i32 2
	store i8 108, i8* %strVar2
	%strVar3 = getelementptr i8, i8* %strVar0, i32 3
	store i8 108, i8* %strVar3
	%strVar4 = getelementptr i8, i8* %strVar0, i32 4
	store i8 111, i8* %strVar4
	%strVar5 = getelementptr i8, i8* %strVar0, i32 5
	store i8 32, i8* %strVar5
	%strVar6 = getelementptr i8, i8* %strVar0, i32 6
	store i8 87, i8* %strVar6
	%strVar7 = getelementptr i8, i8* %strVar0, i32 7
	store i8 111, i8* %strVar7
	%strVar8 = getelementptr i8, i8* %strVar0, i32 8
	store i8 114, i8* %strVar8
	%strVar9 = getelementptr i8, i8* %strVar0, i32 9
	store i8 108, i8* %strVar9
	%strVar10 = getelementptr i8, i8* %strVar0, i32 10
	store i8 100, i8* %strVar10
	%strVar11 = getelementptr i8, i8* %strVar0, i32 11
	store i8 33, i8* %strVar11
	%strVar12 = getelementptr i8, i8* %strVar0, i32 12
	store i8 0, i8* %strVar12
	store i8* %strVar0, i8** %hope
	call void @print(i8** %hope)
	ret void
}
define void @main() {
	%temp = alloca i32, align 1
	%t2 = add i32 2, 2
	store i32 %t2, i32* %temp
	%test = alloca %Test, align 1
	%t3 = alloca %Test
	call void @Test_constructor(%Test* %t3)
	store %Test* %t3, %Test* %test
	br i1 1, label %label0, label %label1
	label0:
		store i32 3, i32* %temp
		br label %label2
	label1:
		br label %label2
	label2:
	ret void
}
