$(document).ready(function(){
    $('.button-collapse').sideNav();
    $('select').material_select();
    $('.dropdown-button').dropdown({
            inDuration: 300,
            outDuration: 225,
            constrainWidth: false, // Does not change width of dropdown to that of the activator
            hover: true, // Activate on hover
            gutter: 0, // Spacing from edge
            belowOrigin: true, // Displays dropdown below the button
            alignment: 'left', // Displays dropdown with edge aligned to the left of button
            stopPropagation: false // Stops event propagation
        }
    );
    $('.pushpin-demo-nav').each(function() {
        var $this = $(this);
        var $target = $('#' + $(this).attr('data-target'));
        $this.pushpin({
            top: $target.offset().top,
            bottom: $target.offset().top + $target.outerHeight() - $this.height()
        });
    });
    $('.slider').slider();
    $('.parallax').parallax();
    $('.materialboxed').materialbox();
    $('.target').pushpin({
        top: 0,
        bottom: 1000,
        offset: 0
    });



});

function test(){
    alert('test');
}

