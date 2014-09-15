        .data
        .globl  LF
LF:     .fill   1,4,0                   # int LF
        .text
        .globl  gcd                     
gcd:    enter   $0,$0                   # Start function gcd
.L0001:                                 # Start while-statement
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setne   %al                     # Test !=
        cmpl    $0,%eax                 
        je      .L0002                  
                                        # Start if-statement
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setl    %al                     # Test <
        cmpl    $0,%eax                 
        je      .L0004                  
        leal    12(%ebp),%eax           # b
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        pushl   %eax                    
        movl    8(%ebp),%eax            # a
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # Compute -
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        jmp     .L0003                  
.L0004:                                 #   else-part
        leal    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # Compute -
        popl    %edx                    
        movl    %eax,(%edx)             #   =
.L0003:                                 # End if-statement
        jmp     .L0001                  
.L0002:                                 # End while-statement
        movl    8(%ebp),%eax            # a
        jmp     .exit$gcd               # Return-statement
.exit$gcd:
        leave                           
        ret                             # End function gcd
        .globl  main                    
main:   enter   $8,$0                   # Start function main
        leal    LF,%eax                 # LF
        pushl   %eax                    
        movl    $10,%eax                # 10
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        movl    $35,%eax                # 35
        pushl   %eax                    # Push parameter #1
        call    putchar                 # Call putchar
        addl    $4,%esp                 # Remove parameters
        movl    $32,%eax                # 32
        pushl   %eax                    # Push parameter #1
        call    putchar                 # Call putchar
        addl    $4,%esp                 # Remove parameters
        leal    -4(%ebp),%eax           # v1
        pushl   %eax                    
        call    getint                  # Call getint
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        leal    -8(%ebp),%eax           # v2
        pushl   %eax                    
        call    getint                  # Call getint
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        movl    -8(%ebp),%eax           # v2
        pushl   %eax                    # Push parameter #2
        movl    -4(%ebp),%eax           # v1
        pushl   %eax                    # Push parameter #1
        call    gcd                     # Call gcd
        addl    $8,%esp                 # Remove parameters
        pushl   %eax                    # Push parameter #1
        call    putint                  # Call putint
        addl    $4,%esp                 # Remove parameters
        movl    LF,%eax                 # LF
        pushl   %eax                    # Push parameter #1
        call    putchar                 # Call putchar
        addl    $4,%esp                 # Remove parameters
        movl    $0,%eax                 # 0
        pushl   %eax                    # Push parameter #1
        call    exit                    # Call exit
        addl    $4,%esp                 # Remove parameters
.exit$main:
        leave                           
        ret                             # End function main
