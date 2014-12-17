        .globl  print                   
print:  enter   $0,$0                   # Start function print ;
        movl    8(%ebp),%eax            # x
        pushl   %eax                    # Push Parameter #1
        call    putint                  # call putint
        addl    $4,%esp                 # Remove parameter
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        movl    12(%ebp),%eax           # op1
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
                                        # Start If-statement
        movl    16(%ebp),%eax           # op2
        pushl   %eax                    
        movl    $32,%eax                # 32
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setne   %al                     # Test !=
        cmpl    $0,%eax                 
        je      .L0001                  
        movl    16(%ebp),%eax           # op2
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
.L0001:                                 
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        movl    20(%ebp),%eax           # y
        pushl   %eax                    # Push Parameter #1
        call    putint                  # call putint
        addl    $4,%esp                 # Remove parameter
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        movl    $61,%eax                # 61
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        movl    24(%ebp),%eax           # res
        pushl   %eax                    # Push Parameter #1
        call    putint                  # call putint
        addl    $4,%esp                 # Remove parameter
        movl    $10,%eax                # 10
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
.exit$print:                                
        leave                           
        ret                             # end print
        .globl  test                    
test:   enter   $0,$0                   # Start function test ;
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        pushl   %eax                    # Push Parameter #5
        movl    12(%ebp),%eax           # b
        pushl   %eax                    # Push Parameter #4
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #3
        movl    $43,%eax                # 43
        pushl   %eax                    # Push Parameter #2
        movl    8(%ebp),%eax            # a
        pushl   %eax                    # Push Parameter #1
        call    print                   # call print
        addl    $4,%esp                 # Remove parameter
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # Compute -
        pushl   %eax                    # Push Parameter #5
        movl    12(%ebp),%eax           # b
        pushl   %eax                    # Push Parameter #4
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #3
        movl    $45,%eax                # 45
        pushl   %eax                    # Push Parameter #2
        movl    8(%ebp),%eax            # a
        pushl   %eax                    # Push Parameter #1
        call    print                   # call print
        addl    $4,%esp                 # Remove parameter
        movl    8(%ebp),%eax            # a
        movl    %eax,%eax               
        popl    %eax                    
        imull   %ecx,%eax               # Multiply
        movl    12(%ebp),%eax           # b
        pushl   %eax                    # Push Parameter #5
        movl    12(%ebp),%eax           # b
        pushl   %eax                    # Push Parameter #4
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #3
        movl    $42,%eax                # 42
        pushl   %eax                    # Push Parameter #2
        movl    8(%ebp),%eax            # a
        pushl   %eax                    # Push Parameter #1
        call    print                   # call print
        addl    $4,%esp                 # Remove parameter
        movl    8(%ebp),%eax            # a
        movl    %eax,%eax               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    # Divide
        movl    12(%ebp),%eax           # b
        pushl   %eax                    # Push Parameter #5
        movl    12(%ebp),%eax           # b
        pushl   %eax                    # Push Parameter #4
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #3
        movl    $47,%eax                # 47
        pushl   %eax                    # Push Parameter #2
        movl    8(%ebp),%eax            # a
        pushl   %eax                    # Push Parameter #1
        call    print                   # call print
        addl    $4,%esp                 # Remove parameter
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        sete    %al                     # Test ==
        pushl   %eax                    # Push Parameter #5
        movl    12(%ebp),%eax           # b
        pushl   %eax                    # Push Parameter #4
        movl    $61,%eax                # 61
        pushl   %eax                    # Push Parameter #3
        movl    $61,%eax                # 61
        pushl   %eax                    # Push Parameter #2
        movl    8(%ebp),%eax            # a
        pushl   %eax                    # Push Parameter #1
        call    print                   # call print
        addl    $4,%esp                 # Remove parameter
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setne   %al                     # Test !=
        pushl   %eax                    # Push Parameter #5
        movl    12(%ebp),%eax           # b
        pushl   %eax                    # Push Parameter #4
        movl    $61,%eax                # 61
        pushl   %eax                    # Push Parameter #3
        movl    $33,%eax                # 33
        pushl   %eax                    # Push Parameter #2
        movl    8(%ebp),%eax            # a
        pushl   %eax                    # Push Parameter #1
        call    print                   # call print
        addl    $4,%esp                 # Remove parameter
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setl    %al                     # Test <
        pushl   %eax                    # Push Parameter #5
        movl    12(%ebp),%eax           # b
        pushl   %eax                    # Push Parameter #4
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #3
        movl    $60,%eax                # 60
        pushl   %eax                    # Push Parameter #2
        movl    8(%ebp),%eax            # a
        pushl   %eax                    # Push Parameter #1
        call    print                   # call print
        addl    $4,%esp                 # Remove parameter
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setle   %al                     # Test <=
        pushl   %eax                    # Push Parameter #5
        movl    12(%ebp),%eax           # b
        pushl   %eax                    # Push Parameter #4
        movl    $61,%eax                # 61
        pushl   %eax                    # Push Parameter #3
        movl    $60,%eax                # 60
        pushl   %eax                    # Push Parameter #2
        movl    8(%ebp),%eax            # a
        pushl   %eax                    # Push Parameter #1
        call    print                   # call print
        addl    $4,%esp                 # Remove parameter
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setg    %al                     # Test >
        pushl   %eax                    # Push Parameter #5
        movl    12(%ebp),%eax           # b
        pushl   %eax                    # Push Parameter #4
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #3
        movl    $62,%eax                # 62
        pushl   %eax                    # Push Parameter #2
        movl    8(%ebp),%eax            # a
        pushl   %eax                    # Push Parameter #1
        call    print                   # call print
        addl    $4,%esp                 # Remove parameter
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setge   %al                     # Test >=
        pushl   %eax                    # Push Parameter #5
        movl    12(%ebp),%eax           # b
        pushl   %eax                    # Push Parameter #4
        movl    $61,%eax                # 61
        pushl   %eax                    # Push Parameter #3
        movl    $62,%eax                # 62
        pushl   %eax                    # Push Parameter #2
        movl    8(%ebp),%eax            # a
        pushl   %eax                    # Push Parameter #1
        call    print                   # call print
        addl    $4,%esp                 # Remove parameter
.exit$test:                                
        leave                           
        ret                             # end test
        .globl  main                    
main:   enter   $32,$0                  # Start function main ;
        movl    $0,%eax                 # 0
        pushl   %eax                    
        movl    $0,%eax                 # 0
        leal    -12(%ebp),%edx          # va[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $3,%eax                 # 3
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    $1,%eax                 # 1
        pushl   %eax                    
        movl    $1,%eax                 # 1
        leal    -12(%ebp),%edx          # va[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    $2,%eax                 # 2
        pushl   %eax                    
        movl    $2,%eax                 # 2
        leal    -12(%ebp),%edx          # va[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $17,%eax                # 17
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    $0,%eax                 # 0
        pushl   %eax                    
        movl    $0,%eax                 # 0
        leal    -24(%ebp),%edx          # vb[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $32,%eax                # 32
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    $1,%eax                 # 1
        pushl   %eax                    
        movl    $1,%eax                 # 1
        leal    -24(%ebp),%edx          # vb[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $2,%eax                 # 2
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    $2,%eax                 # 2
        pushl   %eax                    
        movl    $2,%eax                 # 2
        leal    -24(%ebp),%edx          # vb[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $17,%eax                # 17
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -28(%ebp),%eax          # ia
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0003:                                 # Start For-statement
        movl    -28(%ebp),%eax          # ia
        pushl   %eax                    
        movl    $3,%eax                 # 3
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setl    %al                     # Test <
        cmpl    $0,%eax                 
        je      .L0004                  
        leal    -32(%ebp),%eax          # ib
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0005:                                 # Start For-statement
        movl    -32(%ebp),%eax          # ib
        pushl   %eax                    
        movl    $3,%eax                 # 3
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setl    %al                     # Test <
        cmpl    $0,%eax                 
        je      .L0006                  
        movl    -32(%ebp),%eax          # ib
        leal    -24(%ebp),%edx          # vb[index]
        movl    (%edx,%eax,4),%eax      
        pushl   %eax                    # Push Parameter #2
        movl    -28(%ebp),%eax          # ia
        leal    -12(%ebp),%edx          # va[index]
        movl    (%edx,%eax,4),%eax      
        pushl   %eax                    # Push Parameter #1
        call    test                    # call test
        addl    $4,%esp                 # Remove parameter
        leal    -32(%ebp),%eax          # ib
        pushl   %eax                    
        movl    -32(%ebp),%eax          # ib
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        jmp     .L0005                  
.L0006:                                 # End For-statement
        leal    -28(%ebp),%eax          # ia
        pushl   %eax                    
        movl    -28(%ebp),%eax          # ia
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        jmp     .L0003                  
.L0004:                                 # End For-statement
.exit$main:                                
        leave                           
        ret                             # end main
