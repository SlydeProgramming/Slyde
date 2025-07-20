

### Download Instructions

Go to relesess and download the latest installer for your operating system (currently only supports windows)

Unzip the folder and run the installer.bat with admin permisions and your good to go!

Also install the Slyde Language extention on vs code!


```
slyde (build | run) %PATH_TO_.SLY% 
```

 example

```
slyde build hello_world.sly
```

output:

```
Build succeeded. Executable is at: C:\Users\every\Desktop\out.exe
```

### Documentation


Suported Types:
```
boolean, int, void
```

Booleans values:

```
false | true | off | on | no | yes
```


Class declaration & Field Delceration:


example:
```
class Foo{
  int hello = 0;
}
```

constructors:

```
class Foo{
  int hello = 0;

  constructor(){
    //body
  }
}
```

Main Method for startup ONLY ONE PER FILE:

```
class Foo{
  int hello = 0;
}

main(){
   //body
}
```

Method decleration:

```
class Foo{
  int hello = 0;

  void hi(){
    //body;
  }

}
```

IF Statments:

```
class Foo{
  boolean isBoolean = yes;

  Main(){
    if(isBoolean){
      //true branch
    } else {
      //false branch
    }
  }
  
}

```





