; ModuleID = 'slyde_module'
source_filename = "slyde_module"

define i32 @myFunction(i32 %0) {
entry:
  %sum = add i32 %0, 5
  ret i32 %sum
}
