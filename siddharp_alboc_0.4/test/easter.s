        .globl  mod                     
mod:    enter   $0,$0                   # Start function mod ;
        movl    8(%ebp),%eax            # x
        pushl   %eax                    
        movl    8(%ebp),%eax            # x
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    # Divide
        movl    12(%ebp),%eax           # y
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               # Multiply
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # Compute -
        jmp     .exit$mod               # Return-Statement
.exit$mod:                                
        leave                           
        ret                             # end mod
        .globl  easter                  
easter: enter   $80,$0                  # Start function easter ;
        leal    -4(%ebp),%eax           # a
        pushl   %eax                    
        movl    $19,%eax                # 19
        pushl   %eax                    # Push Parameter #2
        movl    8(%ebp),%eax            # y
        pushl   %eax                    # Push Parameter #1
        call    mod                     # call mod
        addl    $8,%esp                 # Remove parameter
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -8(%ebp),%eax           # b
        pushl   %eax                    
        movl    8(%ebp),%eax            # y
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    # Divide
        movl    $100,%eax               # 100
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -12(%ebp),%eax          # c
        pushl   %eax                    
        movl    $100,%eax               # 100
        pushl   %eax                    # Push Parameter #2
        movl    8(%ebp),%eax            # y
        pushl   %eax                    # Push Parameter #1
        call    mod                     # call mod
        addl    $8,%esp                 # Remove parameter
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -16(%ebp),%eax          # d
        pushl   %eax                    
        movl    -8(%ebp),%eax           # b
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    # Divide
        movl    $4,%eax                 # 4
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -20(%ebp),%eax          # e
        pushl   %eax                    
        movl    $4,%eax                 # 4
        pushl   %eax                    # Push Parameter #2
        movl    -8(%ebp),%eax           # b
        pushl   %eax                    # Push Parameter #1
        call    mod                     # call mod
        addl    $8,%esp                 # Remove parameter
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -24(%ebp),%eax          # f
        pushl   %eax                    
        movl    -8(%ebp),%eax           # b
        pushl   %eax                    
        movl    $8,%eax                 # 8
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    # Divide
        movl    $25,%eax                # 25
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -28(%ebp),%eax          # g
        pushl   %eax                    
        movl    -8(%ebp),%eax           # b
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # Compute -
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    # Divide
        movl    $3,%eax                 # 3
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -32(%ebp),%eax          # h
        pushl   %eax                    
        movl    $30,%eax                # 30
        pushl   %eax                    # Push Parameter #2
        movl    $19,%eax                # 19
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               # Multiply
        movl    -4(%ebp),%eax           # a
        pushl   %eax                    
        movl    $15,%eax                # 15
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        pushl   %eax                    # Push Parameter #1
        call    mod                     # call mod
        addl    $8,%esp                 # Remove parameter
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -36(%ebp),%eax          # i
        pushl   %eax                    
        movl    -12(%ebp),%eax          # c
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    # Divide
        movl    $4,%eax                 # 4
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -40(%ebp),%eax          # k
        pushl   %eax                    
        movl    $4,%eax                 # 4
        pushl   %eax                    # Push Parameter #2
        movl    -12(%ebp),%eax          # c
        pushl   %eax                    # Push Parameter #1
        call    mod                     # call mod
        addl    $8,%esp                 # Remove parameter
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -44(%ebp),%eax          # l
        pushl   %eax                    
        movl    $7,%eax                 # 7
        pushl   %eax                    # Push Parameter #2
        movl    $32,%eax                # 32
        pushl   %eax                    
        movl    -40(%ebp),%eax          # k
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        pushl   %eax                    # Push Parameter #1
        call    mod                     # call mod
        addl    $8,%esp                 # Remove parameter
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -48(%ebp),%eax          # m
        pushl   %eax                    
        movl    -4(%ebp),%eax           # a
        pushl   %eax                    
        movl    $22,%eax                # 22
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               # Multiply
        movl    -44(%ebp),%eax          # l
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    # Divide
        movl    $451,%eax               # 451
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -52(%ebp),%eax          # month
        pushl   %eax                    
        movl    -32(%ebp),%eax          # h
        pushl   %eax                    
        movl    $114,%eax               # 114
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    # Divide
        movl    $31,%eax                # 31
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -76(%ebp),%eax          # day
        pushl   %eax                    
        movl    $31,%eax                # 31
        pushl   %eax                    # Push Parameter #2
        movl    -32(%ebp),%eax          # h
        pushl   %eax                    
        movl    $114,%eax               # 114
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        pushl   %eax                    # Push Parameter #1
        call    mod                     # call mod
        addl    $8,%esp                 # Remove parameter
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
                                        # Start If-statement
        movl    -52(%ebp),%eax          # month
        pushl   %eax                    
        movl    $3,%eax                 # 3
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        sete    %al                     # Test ==
        cmpl    $0,%eax                 
        je      .L0002                  
        movl    $0,%eax                 # 0
        leal    -72(%ebp),%edx          # m_name[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $77,%eax                # 77
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    $1,%eax                 # 1
        leal    -72(%ebp),%edx          # m_name[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $97,%eax                # 97
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    $2,%eax                 # 2
        leal    -72(%ebp),%edx          # m_name[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $114,%eax               # 114
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    $3,%eax                 # 3
        leal    -72(%ebp),%edx          # m_name[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $99,%eax                # 99
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    $4,%eax                 # 4
        leal    -72(%ebp),%edx          # m_name[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $104,%eax               # 104
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        jmp     .L0001                  
.L0002:                                 # Else-Part
        movl    $0,%eax                 # 0
        leal    -72(%ebp),%edx          # m_name[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $65,%eax                # 65
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    $1,%eax                 # 1
        leal    -72(%ebp),%edx          # m_name[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $112,%eax               # 112
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    $2,%eax                 # 2
        leal    -72(%ebp),%edx          # m_name[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $114,%eax               # 114
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    $3,%eax                 # 3
        leal    -72(%ebp),%edx          # m_name[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $105,%eax               # 105
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    $4,%eax                 # 4
        leal    -72(%ebp),%edx          # m_name[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $108,%eax               # 108
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0001:                                 # End If-Statement
        movl    -76(%ebp),%eax          # day
        pushl   %eax                    # Push Parameter #1
        call    putint                  # call putint
        addl    $4,%esp                 # Remove parameter
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        leal    -80(%ebp),%eax          # ix
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0003:                                 # Start For-statement
        movl    -80(%ebp),%eax          # ix
        pushl   %eax                    
        movl    $5,%eax                 # 5
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setl    %al                     # Test <
        cmpl    $0,%eax                 
        je      .L0004                  
        movl    -80(%ebp),%eax          # ix
        leal    -72(%ebp),%edx          # m_name[...]
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        leal    -80(%ebp),%eax          # ix
        pushl   %eax                    
        movl    -80(%ebp),%eax          # ix
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        jmp     .L0003                  
.L0004:                                 # End For-statement
.exit$easter:                                
        leave                           
        ret                             # end easter
        .globl  main                    
main:   enter   $4,$0                   # Start function main ;
        leal    -4(%ebp),%eax           # y
        pushl   %eax                    
        movl    $2010,%eax              # 2010
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0005:                                 # Start For-statement
        movl    -4(%ebp),%eax           # y
        pushl   %eax                    
        movl    $2020,%eax              # 2020
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setle   %al                     # Test <=
        cmpl    $0,%eax                 
        je      .L0006                  
        movl    -4(%ebp),%eax           # y
        pushl   %eax                    # Push Parameter #1
        call    easter                  # call easter
        addl    $4,%esp                 # Remove parameter
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        movl    -4(%ebp),%eax           # y
        pushl   %eax                    # Push Parameter #1
        call    putint                  # call putint
        addl    $4,%esp                 # Remove parameter
        movl    $10,%eax                # 10
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        leal    -4(%ebp),%eax           # y
        pushl   %eax                    
        movl    -4(%ebp),%eax           # y
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        jmp     .L0005                  
.L0006:                                 # End For-statement
.exit$main:                                
        leave                           
        ret                             # end main
