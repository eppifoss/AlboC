        .data
        .globl  true
true:   .fill   1,4,0                   # int true
        .globl  false
false:  .fill   1,4,0                   # int false
        .globl  LF
LF:     .fill   1,4,0                   # int LF
        .text
        .globl  my_gets                 
my_gets:
        enter   $8,$0                   # Start function my_gets
        leal    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #   =
.L0001:                                 # Start while-statement
        movl    true,%eax               # true
        cmpl    $0,%eax                 
        je      .L0002                  
        leal    -8(%ebp),%eax           # c
        pushl   %eax                    
        call    getchar                 # Call getchar
        popl    %edx                    
        movl    %eax,(%edx)             #   =
                                        # Start if-statement
        movl    -8(%ebp),%eax           # c
        pushl   %eax                    
        movl    LF,%eax                 # LF
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        sete    %al                     # Test ==
        cmpl    $0,%eax                 
        je      .L0003                  
        movl    -4(%ebp),%eax           # i
        movl    8(%ebp),%edx            # s[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        movl    $0,%eax                 # 0
        jmp     .exit$my_gets           # Return-statement
.L0003:                                 # End if-statement
        movl    -4(%ebp),%eax           # i
        movl    8(%ebp),%edx            # s[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    -8(%ebp),%eax           # c
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        leal    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        jmp     .L0001                  
.L0002:                                 # End while-statement
.exit$my_gets:
        leave                           
        ret                             # End function my_gets
        .globl  p1                      
p1:     enter   $0,$0                   # Start function p1
        movl    8(%ebp),%eax            # c1
        pushl   %eax                    # Push parameter #1
        call    putchar                 # Call putchar
        addl    $4,%esp                 # Remove parameters
.exit$p1:
        leave                           
        ret                             # End function p1
        .globl  p2                      
p2:     enter   $0,$0                   # Start function p2
        movl    8(%ebp),%eax            # c1
        pushl   %eax                    # Push parameter #1
        call    p1                      # Call p1
        addl    $4,%esp                 # Remove parameters
        movl    12(%ebp),%eax           # c2
        pushl   %eax                    # Push parameter #1
        call    p1                      # Call p1
        addl    $4,%esp                 # Remove parameters
.exit$p2:
        leave                           
        ret                             # End function p2
        .globl  p3                      
p3:     enter   $0,$0                   # Start function p3
        movl    12(%ebp),%eax           # c2
        pushl   %eax                    # Push parameter #2
        movl    8(%ebp),%eax            # c1
        pushl   %eax                    # Push parameter #1
        call    p2                      # Call p2
        addl    $8,%esp                 # Remove parameters
        movl    16(%ebp),%eax           # c3
        pushl   %eax                    # Push parameter #1
        call    p1                      # Call p1
        addl    $4,%esp                 # Remove parameters
.exit$p3:
        leave                           
        ret                             # End function p3
        .globl  p4                      
p4:     enter   $0,$0                   # Start function p4
        movl    16(%ebp),%eax           # c3
        pushl   %eax                    # Push parameter #3
        movl    12(%ebp),%eax           # c2
        pushl   %eax                    # Push parameter #2
        movl    8(%ebp),%eax            # c1
        pushl   %eax                    # Push parameter #1
        call    p3                      # Call p3
        addl    $12,%esp                # Remove parameters
        movl    20(%ebp),%eax           # c4
        pushl   %eax                    # Push parameter #1
        call    p1                      # Call p1
        addl    $4,%esp                 # Remove parameters
.exit$p4:
        leave                           
        ret                             # End function p4
        .globl  p12                     
p12:    enter   $0,$0                   # Start function p12
        movl    20(%ebp),%eax           # c4
        pushl   %eax                    # Push parameter #4
        movl    16(%ebp),%eax           # c3
        pushl   %eax                    # Push parameter #3
        movl    12(%ebp),%eax           # c2
        pushl   %eax                    # Push parameter #2
        movl    8(%ebp),%eax            # c1
        pushl   %eax                    # Push parameter #1
        call    p4                      # Call p4
        addl    $16,%esp                # Remove parameters
        movl    36(%ebp),%eax           # c8
        pushl   %eax                    # Push parameter #4
        movl    32(%ebp),%eax           # c7
        pushl   %eax                    # Push parameter #3
        movl    28(%ebp),%eax           # c6
        pushl   %eax                    # Push parameter #2
        movl    24(%ebp),%eax           # c5
        pushl   %eax                    # Push parameter #1
        call    p4                      # Call p4
        addl    $16,%esp                # Remove parameters
        movl    52(%ebp),%eax           # c12
        pushl   %eax                    # Push parameter #4
        movl    48(%ebp),%eax           # c11
        pushl   %eax                    # Push parameter #3
        movl    44(%ebp),%eax           # c10
        pushl   %eax                    # Push parameter #2
        movl    40(%ebp),%eax           # c9
        pushl   %eax                    # Push parameter #1
        call    p4                      # Call p4
        addl    $16,%esp                # Remove parameters
.exit$p12:
        leave                           
        ret                             # End function p12
        .globl  my_puts                 
my_puts:
        enter   $8,$0                   # Start function my_puts
        leal    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #   =
.L0004:                                 # Start while-statement
        movl    -4(%ebp),%eax           # i
        movl    8(%ebp),%edx            # s[...]
        movl    (%edx,%eax,4),%eax      
        cmpl    $0,%eax                 
        je      .L0005                  
        leal    -8(%ebp),%eax           # c
        pushl   %eax                    
        movl    -4(%ebp),%eax           # i
        movl    8(%ebp),%edx            # s[...]
        movl    (%edx,%eax,4),%eax      
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        leal    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        movl    -8(%ebp),%eax           # c
        pushl   %eax                    # Push parameter #1
        call    p1                      # Call p1
        addl    $4,%esp                 # Remove parameters
        jmp     .L0004                  
.L0005:                                 # End while-statement
.exit$my_puts:
        leave                           
        ret                             # End function my_puts
        .globl  my_strlen               
my_strlen:
        enter   $4,$0                   # Start function my_strlen
        leal    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #   =
.L0006:                                 # Start while-statement
        movl    -4(%ebp),%eax           # i
        movl    8(%ebp),%edx            # s[...]
        movl    (%edx,%eax,4),%eax      
        cmpl    $0,%eax                 
        je      .L0007                  
        leal    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        jmp     .L0006                  
.L0007:                                 # End while-statement
        movl    -4(%ebp),%eax           # i
        jmp     .exit$my_strlen         # Return-statement
.exit$my_strlen:
        leave                           
        ret                             # End function my_strlen
        .globl  is_palindrome           
is_palindrome:
        enter   $8,$0                   # Start function is_palindrome
        leal    -4(%ebp),%eax           # i1
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        leal    -8(%ebp),%eax           # i2
        pushl   %eax                    
        movl    8(%ebp),%eax            # s
        pushl   %eax                    # Push parameter #1
        call    my_strlen               # Call my_strlen
        addl    $4,%esp                 # Remove parameters
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # Compute -
        popl    %edx                    
        movl    %eax,(%edx)             #   =
.L0008:                                 # Start while-statement
        movl    -4(%ebp),%eax           # i1
        pushl   %eax                    
        movl    -8(%ebp),%eax           # i2
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setl    %al                     # Test <
        cmpl    $0,%eax                 
        je      .L0009                  
                                        # Start if-statement
        movl    -4(%ebp),%eax           # i1
        movl    8(%ebp),%edx            # s[...]
        movl    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    -8(%ebp),%eax           # i2
        movl    8(%ebp),%edx            # s[...]
        movl    (%edx,%eax,4),%eax      
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setne   %al                     # Test !=
        cmpl    $0,%eax                 
        je      .L0010                  
        movl    false,%eax              # false
        jmp     .exit$is_palindrome     # Return-statement
.L0010:                                 # End if-statement
        leal    -4(%ebp),%eax           # i1
        pushl   %eax                    
        movl    -4(%ebp),%eax           # i1
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        leal    -8(%ebp),%eax           # i2
        pushl   %eax                    
        movl    -8(%ebp),%eax           # i2
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # Compute -
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        jmp     .L0008                  
.L0009:                                 # End while-statement
        movl    true,%eax               # true
        jmp     .exit$is_palindrome     # Return-statement
.exit$is_palindrome:
        leave                           
        ret                             # End function is_palindrome
        .globl  main                    
main:   enter   $808,$0                 # Start function main
        leal    false,%eax              # false
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        leal    true,%eax               # true
        pushl   %eax                    
        movl    $1,%eax                 # 1
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        leal    LF,%eax                 # LF
        pushl   %eax                    
        movl    $10,%eax                # 10
        popl    %edx                    
        movl    %eax,(%edx)             #   =
.L0011:                                 # Start while-statement
        movl    true,%eax               # true
        cmpl    $0,%eax                 
        je      .L0012                  
        movl    $32,%eax                # 32
        pushl   %eax                    # Push parameter #2
        movl    $63,%eax                # 63
        pushl   %eax                    # Push parameter #1
        call    p2                      # Call p2
        addl    $8,%esp                 # Remove parameters
        leal    -804(%ebp),%eax         # s1
        pushl   %eax                    # Push parameter #1
        call    my_gets                 # Call my_gets
        addl    $4,%esp                 # Remove parameters
                                        # Start if-statement
        leal    -804(%ebp),%eax         # s1
        pushl   %eax                    # Push parameter #1
        call    my_strlen               # Call my_strlen
        addl    $4,%esp                 # Remove parameters
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        sete    %al                     # Test ==
        cmpl    $0,%eax                 
        je      .L0013                  
        movl    $0,%eax                 # 0
        pushl   %eax                    # Push parameter #1
        call    exit                    # Call exit
        addl    $4,%esp                 # Remove parameters
.L0013:                                 # End if-statement
        movl    $39,%eax                # 39
        pushl   %eax                    # Push parameter #1
        call    p1                      # Call p1
        addl    $4,%esp                 # Remove parameters
        leal    -804(%ebp),%eax         # s1
        pushl   %eax                    # Push parameter #1
        call    my_puts                 # Call my_puts
        addl    $4,%esp                 # Remove parameters
        movl    $32,%eax                # 32
        pushl   %eax                    # Push parameter #2
        movl    $39,%eax                # 39
        pushl   %eax                    # Push parameter #1
        call    p2                      # Call p2
        addl    $8,%esp                 # Remove parameters
        movl    $32,%eax                # 32
        pushl   %eax                    # Push parameter #3
        movl    $115,%eax               # 115
        pushl   %eax                    # Push parameter #2
        movl    $105,%eax               # 105
        pushl   %eax                    # Push parameter #1
        call    p3                      # Call p3
        addl    $12,%esp                # Remove parameters
        leal    -808(%ebp),%eax         # no_p
        pushl   %eax                    
        leal    -804(%ebp),%eax         # s1
        pushl   %eax                    # Push parameter #1
        call    is_palindrome           # Call is_palindrome
        addl    $4,%esp                 # Remove parameters
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        sete    %al                     # Test ==
        popl    %edx                    
        movl    %eax,(%edx)             #   =
                                        # Start if-statement
        movl    -808(%ebp),%eax         # no_p
        cmpl    $0,%eax                 
        je      .L0014                  
        movl    $32,%eax                # 32
        pushl   %eax                    # Push parameter #3
        movl    $111,%eax               # 111
        pushl   %eax                    # Push parameter #2
        movl    $110,%eax               # 110
        pushl   %eax                    # Push parameter #1
        call    p3                      # Call p3
        addl    $12,%esp                # Remove parameters
.L0014:                                 # End if-statement
        movl    LF,%eax                 # LF
        pushl   %eax                    # Push parameter #12
        movl    $46,%eax                # 46
        pushl   %eax                    # Push parameter #11
        movl    $101,%eax               # 101
        pushl   %eax                    # Push parameter #10
        movl    $109,%eax               # 109
        pushl   %eax                    # Push parameter #9
        movl    $111,%eax               # 111
        pushl   %eax                    # Push parameter #8
        movl    $114,%eax               # 114
        pushl   %eax                    # Push parameter #7
        movl    $100,%eax               # 100
        pushl   %eax                    # Push parameter #6
        movl    $110,%eax               # 110
        pushl   %eax                    # Push parameter #5
        movl    $105,%eax               # 105
        pushl   %eax                    # Push parameter #4
        movl    $108,%eax               # 108
        pushl   %eax                    # Push parameter #3
        movl    $97,%eax                # 97
        pushl   %eax                    # Push parameter #2
        movl    $112,%eax               # 112
        pushl   %eax                    # Push parameter #1
        call    p12                     # Call p12
        addl    $48,%esp                # Remove parameters
        jmp     .L0011                  
.L0012:                                 # End while-statement
.exit$main:
        leave                           
        ret                             # End function main
