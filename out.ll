;  ============== Generated LLVM ===============
declare i32 @puts(i8*)

define void @print(i8* %str) {
   call i32 @puts(i8* %str)
   ret void
}

;  Test
%Test = type {i8*}
@"Hello World!" = private constant [13 x i8] c"Hello World!\00"

;  ============== Test ===============
define void @Test_constructor(%Test* %this)   {
	  %ptr_test = getelementptr inbounds %Test, %Test* %this, i32 0, i32 0
	%Test_constructor_test = getelementptr inbounds [13 x i8], [13 x i8]* @"Hello World!", i32 0, i32 0
	store i8* %Test_constructor_test, i8** %ptr_test  %val_test = load i8*, i8** %ptr_test
	call void @print(i8* %Test_constructor_test)
    ret void
}

;  ============== Main Method ===============
define void @main()   {
	%obj = alloca %Test

	call void @Test_constructor(%Test* %obj)

    ret void
}
	;  ============== EOF ===============
