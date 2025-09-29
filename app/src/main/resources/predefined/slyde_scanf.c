#include <stdio.h>
#include <stdarg.h>

int slyde_scanf(const char *fmt, ...) {
    va_list args;
    va_start(args, fmt);

    char fixed_fmt[32];
    snprintf(fixed_fmt, sizeof(fixed_fmt), " %s", fmt);  // space skips leftover newlines

    int count = vscanf(fixed_fmt, args);
    va_end(args);
    return count;
}
