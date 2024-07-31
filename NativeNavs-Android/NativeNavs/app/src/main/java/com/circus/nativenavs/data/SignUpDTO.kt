package com.circus.nativenavs.data

data class SignUpDTO(
    val email: String,
    val password: String,
    val isNav: Boolean,
    val nickname: String,
    val language: List<String>,
    val name: String,
    val phone: String,
    val nation: String,
    val birth : String,
    val isKorean : Boolean
)
{
    @Override
    override fun toString(): String {
        return "이메일 : " + email +
        "\n 비밀번호 : " + password +
        "\n 유저타입 : " + isNav +
        "\n 닉네임 : " + nickname +
        "\n 구사가능언어 : " + language +
        "\n 이름 : " + name +
        "\n 휴대폰 : " + phone +
        "\n 국가 : " + nation +
        "\n 생년월일 : " + birth +
        "\n 앱 설정 언어 : " + isKorean
    }
}
