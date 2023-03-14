

$(document).ready(function () {


    $(document).on('click', '.publish-btn', function () {
        let postId = $(this).attr("id");
        $('#publish-a').attr("href", "/admin/published/" + postId);
        $('#modal-lg').modal('show');
    });



    // validate post category form
    if ($("#post_category_form").length) {
        console.log("category form found")
        $('#post_category_form').validate( {
            rules: {
                categoryName: "required",
            },
            messages: {
                categoryName: "Please enter your Post Category name",
            },
            errorElement: "em",
            errorPlacement: function ( error, element ) {
                // Add the `help-block` class to the error element
                error.addClass( "help-block" );

                if ( element.prop( "type" ) === "checkbox" ) {
                    error.insertAfter( element.parent( "label" ) );
                } else {
                    error.insertAfter( element );
                }
            },
            highlight: function ( element, errorClass, validClass ) {
                $( element ).parents( ".col-sm-5" ).addClass( "has-error" ).removeClass( "has-success" );
            },
            unhighlight: function (element, errorClass, validClass) {
                $( element ).parents( ".col-sm-5" ).addClass( "has-success" ).removeClass( "has-error" );
            }
        } );
    }

    if ($("#add_user_form").length) {
        console.log("user form found")
        $('#add_user_form').validate( {
            rules: {
                userName: "required",
                email: "required",
                role: "required",
                password: {
                    required: true,
                    minlength: 4
                },
                confirmPassword: {
                    minlength: 4,
                    equalTo: "#password"
                }
            },
            messages: {
                userName: "Please enter user name",
                email: "Please enter email",
                role: "Please select",
                password: {
                    required: "Please enter password",
                    minlength: "Min 4 Character"
                },
                confirmPassword: {
                    minlength: "Min 4 Character",
                    equalTo : "Password not match"
                }
            },
            errorElement: "em",
            errorPlacement: function ( error, element ) {
                // Add the `help-block` class to the error element
                error.addClass( "help-block" );

                if ( element.prop( "type" ) === "checkbox" ) {
                    error.insertAfter( element.parent( "label" ) );
                } else {
                    error.insertAfter( element );
                }
            },
            highlight: function ( element, errorClass, validClass ) {
                $( element ).parents( ".col-sm-5" ).addClass( "has-error" ).removeClass( "has-success" );
            },
            unhighlight: function (element, errorClass, validClass) {
                $( element ).parents( ".col-sm-5" ).addClass( "has-success" ).removeClass( "has-error" );
            }
        } );
    }


    if ($("#change_password_form").length) {
        console.log("change password form found")
        $('#change_password_form').validate( {
            rules: {
                oldPassword: "required",
                password: {
                    required: true,
                    minlength: 4
                },
                confirmPassword: {
                    minlength: 4,
                    equalTo: '[name="password"]'
                }
            },
            messages: {
                oldPassword: "Please enter your old password",
                password: {
                    required: "Please enter new password",
                    minlength: "Min 4 Character"
                },
                confirmPassword: {
                    minlength: "Min 4 Character",
                    equalTo : "Password not match"
                }
            },
            errorElement: "em",
            errorPlacement: function ( error, element ) {
                // Add the `help-block` class to the error element
                error.addClass( "help-block" );

                if ( element.prop( "type" ) === "checkbox" ) {
                    error.insertAfter( element.parent( "label" ) );
                } else {
                    error.insertAfter( element );
                }
            },
            highlight: function ( element, errorClass, validClass ) {
                $( element ).parents( ".col-sm-5" ).addClass( "has-error" ).removeClass( "has-success" );
            },
            unhighlight: function (element, errorClass, validClass) {
                $( element ).parents( ".col-sm-5" ).addClass( "has-success" ).removeClass( "has-error" );
            }
        } );
    }



    // validating add post form
    if ($("#quickForm").length) {
        console.log("category form found")
        $('#quickForm').validate( {
            rules: {
                subject: {
                    required: true
                },
                date: {
                    required: true
                },
                letterPdf: {
                    required: true,
                    extension: 'pdf'
                },
            },
            messages: {
                title: {
                    required : "Please enter Subject"
                },
                content: {
                    required : "Please select date"
                },
                coverImage: {
                    required : "Please select the file",
                    extension: 'Only pdf file is allowed'
                },


            },
            errorElement: "em",
            errorPlacement: function ( error, element ) {
                // Add the `help-block` class to the error element
                error.addClass( "help-block" );
                console.log(element.prop( "type" ));

                if ( element.prop( "type" ) === "checkbox" ) {
                    error.insertAfter( element.parent( "label" ) );
                }else if ( element.prop( "type" ) === "select-multiple" )
                {
                    error.appendTo( element.parents('.form-group') );
                }
                else if ( element.is(":radio") )
                {
                    error.appendTo( element.parents('.form-group') );
                }
                else if ( element.is(":file") )
                {
                    error.appendTo( element.parents('.form-group') );
                }
                else {
                    error.insertAfter( element );
                }
            },
            highlight: function ( element, errorClass, validClass ) {
                $( element ).parents( ".col-sm-5" ).addClass( "has-error" ).removeClass( "has-success" );
            },
            unhighlight: function (element, errorClass, validClass) {
                $( element ).parents( ".col-sm-5" ).addClass( "has-success" ).removeClass( "has-error" );
            }
        } );
    }

    //validate file extension custom  method.
    jQuery.validator.addMethod("extension", function (value, element, param) {
        param = typeof param === "string" ? param.replace(/,/g, '|') : "png|jpg";
        return this.optional(element) || value.match(new RegExp(".(" + param + ")$", "i"));
    }, "Please enter a value with a valid extension.");

});
