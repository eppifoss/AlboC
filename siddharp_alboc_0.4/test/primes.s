        .data                           
        .globl  prime                   
prime:  .fill   4004,4,0                
        .globl  LF                      
LF:     .fill   1,4,0                   
        .text                           
        .globl  find_primes             
find_primes:
        enter   $8,$0                   # Start function find_primes ;
        leal    -4(%ebp),%eax           # i1
        pushl   %eax                    
        movl    $2,%eax                 # 2
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0001:                                 # Start For-statement
        movl    -4(%ebp),%eax           # i1
        pushl   %eax                    
        movl    $1000,%eax              # 1000
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setle   %al                     # Test <=
        cmpl    $0,%eax                 
        je      .L0002                  
        leal    -8(%ebp),%eax           # i2
        pushl   %eax                    
        movl    $2,%eax                 # 2
        movl    %eax,%eax               
        popl    %eax                    
        imull   %ecx,%eax               # Multiply
        movl    -4(%ebp),%eax           # i1
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0003:                                 # Start For-statement
        movl    -8(%ebp),%eax           # i2
        pushl   %eax                    
        movl    $1000,%eax              # 1000
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setle   %al                     # Test <=
        cmpl    $0,%eax                 
        je      .L0004                  
        movl    -8(%ebp),%eax           # i2
        pushl   %eax                    
        movl    -8(%ebp),%eax           # i2
        leal    prime,%edx              # prime[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -8(%ebp),%eax           # i2
        pushl   %eax                    
        movl    -8(%ebp),%eax           # i2
        pushl   %eax                    
        movl    -4(%ebp),%eax           # i1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        jmp     .L0003                  
.L0004:                                 # End For-statement
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
        jmp     .L0001                  
.L0002:                                 # End For-statement
.exit$find_primes:                                
        leave                           
        ret                             # end find_primes
        .globl  mod                     
mod:    enter   $0,$0                   # Start function mod ;
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    8(%ebp),%eax            # a
        movl    %eax,%eax               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    # Divide
        movl    12(%ebp),%eax           # b
        movl    %eax,%eax               
        popl    %eax                    
        imull   %ecx,%eax               # Multiply
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # Compute -
        jmp     .exit$mod               # Return-Statement
.exit$mod:                                
        leave                           
        ret                             # end mod
        .globl  n_chars                 
n_chars:
        enter   $0,$0                   # Start function n_chars ;
                                        # Start If-statement
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setl    %al                     # Test <
        cmpl    $0,%eax                 
        je      .L0005                  
        movl    $1,%eax                 # 1
        pushl   %eax                    
        movl    8(%ebp),%eax            # a
        pushl   %eax                    # Push Parameter #1
        call    n_chars                 # call n_chars
        addl    $4,%esp                 # Remove parameter
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        jmp     .exit$n_chars           # Return-Statement
.L0005:                                 
                                        # Start If-statement
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    $9,%eax                 # 9
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setle   %al                     # Test <=
        cmpl    $0,%eax                 
        je      .L0007                  
        movl    $1,%eax                 # 1
        jmp     .exit$n_chars           # Return-Statement
.L0007:                                 
        movl    8(%ebp),%eax            # a
        movl    %eax,%eax               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    # Divide
        movl    $10,%eax                # 10
        pushl   %eax                    # Push Parameter #1
        call    n_chars                 # call n_chars
        addl    $4,%esp                 # Remove parameter
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        jmp     .exit$n_chars           # Return-Statement
.exit$n_chars:                                
        leave                           
        ret                             # end n_chars
        .globl  pn                      
pn:     enter   $4,$0                   # Start function pn ;
        leal    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    8(%ebp),%eax            # v
        pushl   %eax                    # Push Parameter #1
        call    n_chars                 # call n_chars
        addl    $4,%esp                 # Remove parameter
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0009:                                 # Start For-statement
        movl    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    12(%ebp),%eax           # w
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setle   %al                     # Test <=
        cmpl    $0,%eax                 
        je      .L0010                  
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
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
        jmp     .L0009                  
.L0010:                                 # End For-statement
        movl    8(%ebp),%eax            # v
        pushl   %eax                    # Push Parameter #1
        call    putint                  # call putint
        addl    $4,%esp                 # Remove parameter
.exit$pn:                                
        leave                           
        ret                             # end pn
        .globl  and                     
and:    enter   $0,$0                   # Start function and ;
                                        # Start If-statement
        movl    8(%ebp),%eax            # a
        cmpl    $0,%eax                 
        je      .L0012                  
        movl    12(%ebp),%eax           # b
        jmp     .exit$and               # Return-Statement
        jmp     .L0011                  
.L0012:                                 # Else-Part
        movl    $0,%eax                 # 0
        jmp     .exit$and               # Return-Statement
.L0011:                                 # End If-Statement
.exit$and:                                
        leave                           
        ret                             # end and
        .globl  print_primes            
print_primes:
        enter   $8,$0                   # Start function print_primes ;
        leal    -4(%ebp),%eax           # n_printed
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -8(%ebp),%eax           # i
        pushl   %eax                    
        movl    $1,%eax                 # 1
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0013:                                 # Start For-statement
        movl    -8(%ebp),%eax           # i
        pushl   %eax                    
        movl    $1000,%eax              # 1000
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setle   %al                     # Test <=
        cmpl    $0,%eax                 
        je      .L0014                  
                                        # Start If-statement
        movl    -8(%ebp),%eax           # i
        leal    prime,%edx              # prime[index]
        movl    (%edx,%eax,4),%eax      
        cmpl    $0,%eax                 
        je      .L0015                  
                                        # Start If-statement
        movl    -4(%ebp),%eax           # n_printed
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setg    %al                     # Test >
        pushl   %eax                    # Push Parameter #2
        movl    $10,%eax                # 10
        pushl   %eax                    # Push Parameter #2
        movl    -4(%ebp),%eax           # n_printed
        pushl   %eax                    # Push Parameter #1
        call    mod                     # call mod
        addl    $4,%esp                 # Remove parameter
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        sete    %al                     # Test ==
        pushl   %eax                    # Push Parameter #1
        call    and                     # call and
        addl    $4,%esp                 # Remove parameter
        cmpl    $0,%eax                 
        je      .L0017                  
        movl    LF,%eax                 # LF
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
.L0017:                                 
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        movl    $3,%eax                 # 3
        pushl   %eax                    # Push Parameter #2
        movl    -8(%ebp),%eax           # i
        pushl   %eax                    # Push Parameter #1
        call    pn                      # call pn
        addl    $4,%esp                 # Remove parameter
        leal    -4(%ebp),%eax           # n_printed
        pushl   %eax                    
        movl    -4(%ebp),%eax           # n_printed
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0015:                                 
        leal    -8(%ebp),%eax           # i
        pushl   %eax                    
        movl    -8(%ebp),%eax           # i
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        jmp     .L0013                  
.L0014:                                 # End For-statement
        movl    LF,%eax                 # LF
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
.exit$print_primes:                                
        leave                           
        ret                             # end print_primes
        .globl  main                    
main:   enter   $4,$0                   # Start function main ;
        leal    LF,%eax                 # LF
        pushl   %eax                    
        movl    $10,%eax                # 10
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    $1,%eax                 # 1
        pushl   %eax                    
        movl    $1,%eax                 # 1
        leal    prime,%edx              # prime[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $0,%eax                 # 0
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    $2,%eax                 # 2
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0019:                                 # Start For-statement
        movl    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    $1000,%eax              # 1000
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setle   %al                     # Test <=
        cmpl    $0,%eax                 
        je      .L0020                  
        movl    -4(%ebp),%eax           # i
        pushl   %eax                    
        movl    -4(%ebp),%eax           # i
        leal    prime,%edx              # prime[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $1,%eax                 # 1
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
        jmp     .L0019                  
.L0020:                                 # End For-statement
        call    find_primes             # call find_primes
        call    print_primes            # call print_primes
        movl    $0,%eax                 # 0
        jmp     .exit$main              # Return-Statement
.exit$main:                                
        leave                           
        ret                             # end main
