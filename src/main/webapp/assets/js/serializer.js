/**
 * @author monk
 * @file form data 를 JSON 객체로 바꾸어 주는 모듈입니다
 * @param FormData
 */

/**
 *
 * @param formData
 */
function jsonSerialize(formData) {

    if (!formData instanceof FormData) {
        console.error("parameter is not FormData!")
        return null;
    }

    const json = {};

    for (const key of formData.keys()) {
        json[key] = formData.get(key);
    }


    return json;
}

function jsonSerializeList (formData) {

}

