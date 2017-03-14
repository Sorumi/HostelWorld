/**
 * Created by Sorumi on 17/3/14.
 */
$('#image-file').change(function () {
    $('#image-change').val('1');
    previewImage(this);
});

$('#upload-image').click(function () {
    $('#image-file').click();
});

$('#remove-image').click(function () {
    $('#image-change').val('1');
    removeImage();
});

function previewImage(file) {
    var viewImg = $('#image-view');
    if (file["files"] && file["files"][0]) {
        var reader = new FileReader();
        reader.onload = function (evt) {

            viewImg.css('background-image', 'url(' + evt.target.result + ')');
            viewImg.attr('src', evt.target.result);

            $('#image-view-wrapper').show();
            $("#remove-image").show();
        }
        reader.readAsDataURL(file.files[0]);
    }
}

function removeImage() {
    $('#image-file').val('');
    var viewImg = $('#image-view');
    viewImg.css('background-image', '');
    viewImg.attr('src', '');

    $('#image-view-wrapper').hide();
    $("#remove-image").hide();
}