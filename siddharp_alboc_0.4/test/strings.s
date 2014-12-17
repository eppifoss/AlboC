        .data                           
        .globl  true                    
true:   .fill   1,4,0                   
        .globl  false                   
false:  .fill   1,4,0                   
        .globl  LF                      
LF:     .fill   1,4,0                   
        .text                           
        .globl  my_gets                 
my_gets:
        enter   $8,$0                   # Start function my_gets ;
        leal    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0001:                                 # Start while-statement
        movl    true,%eax               # true
        cmpl    $0,%eax                 
        je      .L0002                  
        leal    -8(%ebp),%eax           # c
        pushl   %eax                    
        call    getchar                 # call getchar
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
                                        # Start If-statement
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
        movl    %eax,(%edx)             #  = 
        movl    $0,%eax                 # 0
        jmp     .exit$my_gets           # Return-Statement
.L0003:                                 
        movl    -4(%ebp),%eax           # i
        movl    8(%ebp),%edx            # s[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    -8(%ebp),%eax           # c
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        jmp     .L0001                  
.L0002:                                 # End while-statement
.exit$my_gets:                                
        leave                           
        ret                             # end my_gets
        .globl  p1                      
p1:     enter   $0,$0                   # Start function p1 ;
        movl    8(%ebp),%eax            # c1
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
.exit$p1:                                
        leave                           
        ret                             # end p1
        .globl  p2                      
p2:     enter   $0,$0                   # Start function p2 ;
        movl    8(%ebp),%eax            # c1
        pushl   %eax                    # Push Parameter #1
        call    p1                      # call p1
        addl    $4,%esp                 # Remove parameter
        movl    12(%ebp),%eax           # c2
        pushl   %eax                    # Push Parameter #1
        call    p1                      # call p1
        addl    $4,%esp                 # Remove parameter
.exit$p2:                                
        leave                           
        ret                             # end p2
        .globl  p3                      
p3:     enter   $0,$0                   # Start function p3 ;
        movl    12(%ebp),%eax           # c2
        pushl   %eax                    # Push Parameter #2
        movl    8(%ebp),%eax            # c1
        pushl   %eax                    # Push Parameter #1
        call    p2                      # call p2
        addl    $8,%esp                 # Remove parameter
        movl    16(%ebp),%eax           # c3
        pushl   %eax                    # Push Parameter #1
        call    p1                      # call p1
        addl    $4,%esp                 # Remove parameter
.exit$p3:                                
        leave                           
        ret                             # end p3
        .globl  p4                      
p4:     enter   $0,$0                   # Start function p4 ;
        movl    16(%ebp),%eax           # c3
        pushl   %eax                    # Push Parameter #3
        movl    12(%ebp),%eax           # c2
        pushl   %eax                    # Push Parameter #2
        movl    8(%ebp),%eax            # c1
        pushl   %eax                    # Push Parameter #1
        call    p3                      # call p3
        addl    $12,%esp                # Remove parameter
        movl    20(%ebp),%eax           # c4
        pushl   %eax                    # Push Parameter #1
        call    p1                      # call p1
        addl    $4,%esp                 # Remove parameter
.exit$p4:                                
        leave                           
        ret                             # end p4
        .globl  p12                     
p12:    enter   $0,$0                   # Start function p12 ;
        movl    20(%ebp),%eax           # c4
        pushl   %eax                    # Push Parameter #4
        movl    16(%ebp),%eax           # c3
        pushl   %eax                    # Push Parameter #3
        movl    12(%ebp),%eax           # c2
        pushl   %eax                    # Push Parameter #2
        movl    8(%ebp),%eax            # c1
        pushl   %eax                    # Push Parameter #1
        call    p4                      # call p4
        addl    $16,%esp                # Remove parameter
        movl    36(%ebp),%eax           # c8
        pushl   %eax                    # Push Parameter #4
        movl    32(%ebp),%eax           # c7
        pushl   %eax                    # Push Parameter #3
        movl    28(%ebp),%eax           # c6
        pushl   %eax                    # Push Parameter #2
        movl    24(%ebp),%eax           # c5
        pushl   %eax                    # Push Parameter #1
        call    p4                      # call p4
        addl    $16,%esp                # Remove parameter
        movl    52(%ebp),%eax           # c12
        pushl   %eax                    # Push Parameter #4
        movl    48(%ebp),%eax           # c11
        pushl   %eax                    # Push Parameter #3
        movl    44(%ebp),%eax           # c10
        pushl   %eax                    # Push Parameter #2
        movl    40(%ebp),%eax           # c9
        pushl   %eax                    # Push Parameter #1
        call    p4                      # call p4
        addl    $16,%esp                # Remove parameter
.exit$p12:                                
        leave                           
        ret                             # end p12
        .globl  my_puts                 
my_puts:
        enter   $8,$0                   # Start function my_puts ;
        leal    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0005:                                 # Start while-statement
        movl    -4(%ebp),%eax           # i
        movl    8(%ebp),%edx            # s[...]
        movl    (%edx,%eax,4),%eax      
        cmpl    $0,%eax                 
        je      .L0006                  
        leal    -8(%ebp),%eax           # c
        pushl   %eax                    
        movl    -4(%ebp),%eax           # i
        movl    8(%ebp),%edx            # s[...]
        movl    (%edx,%eax,4),%eax      
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    -8(%ebp),%eax           # c
        pushl   %eax                    # Push Parameter #1
        call    p1                      # call p1
        addl    $4,%esp                 # Remove parameter
        jmp     .L0005                  
.L0006:                                 # End while-statement
.exit$my_puts:                                
        leave                           
        ret                             # end my_puts
        .globl  my_strlen               
my_strlen:
        enter   $4,$0                   # Start function my_strlen ;
        leal    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0007:                                 # Start while-statement
        movl    -4(%ebp),%eax           # i
        movl    8(%ebp),%edx            # s[...]
        movl    (%edx,%eax,4),%eax      
        cmpl    $0,%eax                 
        je      .L0008                  
        leal    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        jmp     .L0007                  
.L0008:                                 # End while-statement
        movl    -4(%ebp),%eax           # i
        jmp     .exit$my_strlen         # Return-Statement
.exit$my_strlen:                                
        leave                           
        ret                             # end my_strlen
        .globl  is_palindrome           
is_palindrome:
        enter   $8,$0                   # Start function is_palindrome ;
        leal    -4(%ebp),%eax           # i1
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -8(%ebp),%eax           # i2
        pushl   %eax                    
        movl    8(%ebp),%eax            # s
        pushl   %eax                    # Push Parameter #1
        call    my_strlen               # call my_strlen
        addl    $4,%esp                 # Remove parameter
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # Compute -
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0009:                                 # Start while-statement
        movl    -4(%ebp),%eax           # i1
        pushl   %eax                    
        movl    -8(%ebp),%eax           # i2
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setl    %al                     # Test <
        cmpl    $0,%eax                 
        je      .L0010                  
                                        # Start If-statement
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
        je      .L0011                  
        movl    false,%eax              # false
        jmp     .exit$is_palindrome     # Return-Statement
.L0011:                                 
        leal    -4(%ebp),%eax           # i1
        pushl   %eax                    
        movl    -4(%ebp),%eax           # i1
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -8(%ebp),%eax           # i2
        pushl   %eax                    
        movl    -8(%ebp),%eax           # i2
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # Compute -
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        jmp     .L0009                  
.L0010:                                 # End while-statement
        movl    true,%eax               # true
        jmp     .exit$is_palindrome     # Return-Statement
.exit$is_palindrome:                                
        leave                           
        ret                             # end is_palindrome
        .globl  main                    
main:   enter   $808,$0                 # Start function main ;
        leal    false,%eax              # false
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    true,%eax               # true
        pushl   %eax                    
        movl    $1,%eax                 # 1
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    LF,%eax                 # LF
        pushl   %eax                    
        movl    $10,%eax                # 10
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0013:                                 # Start while-statement
        movl    true,%eax               # true
        cmpl    $0,%eax                 
        je      .L0014                  
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #2
        movl    $63,%eax                # 63
        pushl   %eax                    # Push Parameter #1
        call    p2                      # call p2
        addl    $8,%esp                 # Remove parameter
        leal    -804(%ebp),%eax         # s1
        pushl   %eax                    # Push Parameter #1
        call    my_gets                 # call my_gets
        addl    $4,%esp                 # Remove parameter
                                        # Start If-statement
        leal    -804(%ebp),%eax         # s1
        pushl   %eax                    # Push Parameter #1
        call    my_strlen               # call my_strlen
        addl    $4,%esp                 # Remove parameter
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        sete    %al                     # Test ==
        cmpl    $0,%eax                 
        je      .L0015                  
        movl    $0,%eax                 # 0
        pushl   %eax                    # Push Parameter #1
        call    exit                    # call exit
        addl    $4,%esp                 # Remove parameter
.L0015:                                 
        movl    $39,%eax                # 39
        pushl   %eax                    # Push Parameter #1
        call    p1                      # call p1
        addl    $4,%esp                 # Remove parameter
        leal    -804(%ebp),%eax         # s1
        pushl   %eax                    # Push Parameter #1
        call    my_puts                 # call my_puts
        addl    $4,%esp                 # Remove parameter
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #2
        movl    $39,%eax                # 39
        pushl   %eax                    # Push Parameter #1
        call    p2                      # call p2
        addl    $8,%esp                 # Remove parameter
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #3
        movl    $115,%eax               # 115
        pushl   %eax                    # Push Parameter #2
        movl    $105,%eax               # 105
        pushl   %eax                    # Push Parameter #1
        call    p3                      # call p3
        addl    $12,%esp                # Remove parameter
        leal    -808(%ebp),%eax         # no_p
        pushl   %eax                    
        leal    -804(%ebp),%eax         # s1
        pushl   %eax                    # Push Parameter #1
        call    is_palindrome           # call is_palindrome
        addl    $4,%esp                 # Remove parameter
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        sete    %al                     # Test ==
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
                                        # Start If-statement
        movl    -808(%ebp),%eax         # no_p
        cmpl    $0,%eax                 
        je      .L0017                  
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #3
        movl    $111,%eax               # 111
        pushl   %eax                    # Push Parameter #2
        movl    $110,%eax               # 110
        pushl   %eax                    # Push Parameter #1
        call    p3                      # call p3
        addl    $12,%esp                # Remove parameter
.L0017:                                 
        movl    LF,%eax                 # LF
        pushl   %eax                    # Push Parameter #12
        movl    $46,%eax                # 46
        pushl   %eax                    # Push Parameter #11
        movl    $101,%eax               # 101
        pushl   %eax                    # Push Parameter #10
        movl    $109,%eax               # 109
        pushl   %eax                    # Push Parameter #9
        movl    $111,%eax               # 111
        pushl   %eax                    # Push Parameter #8
        movl    $114,%eax               # 114
        pushl   %eax                    # Push Parameter #7
        movl    $100,%eax               # 100
        pushl   %eax                    # Push Parameter #6
        movl    $110,%eax               # 110
        pushl   %eax                    # Push Parameter #5
        movl    $105,%eax               # 105
        pushl   %eax                    # Push Parameter #4
        movl    $108,%eax               # 108
        pushl   %eax                    # Push Parameter #3
        movl    $97,%eax                # 97
        pushl   %eax                    # Push Parameter #2
        movl    $112,%eax               # 112
        pushl   %eax                    # Push Parameter #1
        call    p12                     # call p12
        addl    $48,%esp                # Remove parameter
        jmp     .L0013                  
.L0014:                                 # End while-statement
.exit$main:                                
        leave                           
        ret                             # end main
