#include <GLFW/glfw3.h>

void open_window()
{
    if (!glfwInit())
        return;
    GLFWwindow *window = glfwCreateWindow(640, 480, "Hello Window", NULL, NULL);
    if (!window)
    {
        glfwTerminate();
        return;
    }
    while (!glfwWindowShouldClose(window))
    {
        glfwPollEvents();
    }
    glfwTerminate();
}
