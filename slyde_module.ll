; ModuleID = 'slyde_module'
source_filename = "slyde_module"

@Test = private global { i32, i32 } zeroinitializer
@test2 = private global { i32, i32 } zeroinitializer

define ptr @Test_constructor(i32 %0) {
entry:
  %this = alloca { i32, i32 }, align 8
  %field0 = getelementptr { i32, i32 }, ptr %this, i32 0
  store i32 0, ptr %field0, align 4
  %field1 = getelementptr { i32, i32 }, ptr %this, i32 1
  store i32 0, ptr %field1, align 4
  ret ptr %this
}

define ptr @test2_constructor() {
entry:
  %this = alloca { i32, i32 }, align 8
  %field0 = getelementptr { i32, i32 }, ptr %this, i32 0
  store i32 0, ptr %field0, align 4
  %field1 = getelementptr { i32, i32 }, ptr %this, i32 1
  store i32 0, ptr %field1, align 4
  ret ptr %this
}
