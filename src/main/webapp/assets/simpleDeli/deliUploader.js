/**
 * @author monk
 * @use 간단한 이미지 업로드용 입니다
 * 
 * @use
 * @element input className=deli-image-upload
 * 
 * 
 * 
 * @function simpleDeli.uploader.upload();
 * @class deli-image-upload
 * @use class name deli-image-upload input 의 값들을 서버로 전송합니다
 * 
 * 
 * 
 * @class deli-upload-trigger 
 * @event click
 * @use event 발생시 자동으로 모든 element 들의 이미지를 전송합니다
 * 
 * @class deli-upload-form
 * @event submit
 * @use event 발생시 자동으로 form 내부의 element 들의 이미지를 전송합니다
 */

window.simpleDeli = window.simpleDeli || {};
simpleDeli.uploader = {
    // domain : "http://112.169.196.76:47788",
    replace : true,
    config : {
    },
    addLiveEventListener(type, selector, callback) {
        window.addEventListener(type, function(e){
            console.log(e.target)
            console.log(e.target.classList)
            // console.log(e.target.classList.includes(selector.substring(1)));
            console.log(selector)
            if (e.target.classList.contains(selector.substring(1)) || document.querySelector(selector) === e.target){
                    console.log('success')
                    console.log(this);
                    return callback.bind(e.target)(e);
                }
        })
    },

    uploadImage(form) {
        if (form === null || form === undefined || form.entries().next().done){
            console.log("form data is empty")
            return;
        }

        fetch(config.domain+"/upload",{
            method:"POST",
            body:form
        })
        .then(res => res.json())
        .then(log => console.log(log))
        .catch(err => console.error(err));
    },

    upload(e){
        console.log(this);
        console.log("yell");
        const form = new FormData();
        document.querySelectorAll('.deli-upload-image').forEach(v => {
            if (v === null || v.files[0] === undefined) {
                return;
            }
            const file = v.files[0];
            if (file !== null || file !== undefined){

                if (v.name === "") {
                    v.name = 'img';
                }
                console.log(v.files)
                console.log(v.name)
                form.append(v.name,file, file.name );
            }
            else {
                console.log("hh")
            }
        })
        simpleDeli.uploader.uploadImage(form);
        return;
    },

    uploadForm(e) {
        e.preventDefault();
        const form = new FormData();
        this.querySelectorAll('.deli-upload-image').forEach(v => {
            if (v === null || v.files[0] === undefined) {
                return;
            }
            const file = v.files[0];
            if (file !== null || file !== undefined){

                if (v.name === "") {
                    v.name = 'img';
                }
                console.log(v.files)
                console.log(v.name)
                form.append(v.name,file, file.name );
            }
            else {
                console.log("hh")
            }
        })
        simpleDeli.uploader.uploadImage(form);
        return;
        console.log(this);
    },

    init(){
        console.log("init deli uploader...")
        this.addLiveEventListener('click', '.deli-upload-trigger', this.upload);
        this.addLiveEventListener('submit', '.deli-upload-form', this.uploadForm)
        console.log("deli uploader init complete");
        // window.addEventListener('upload', this.uploader);
    },
}
simpleDeli.uploader.init();


// simpleDeli.uploader.addLiveEventListener('click','.deli-upload', simpleDeli.uploader.upload);

