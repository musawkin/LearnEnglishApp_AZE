package com.example.englishwordsapp.adminPanel

import com.example.englishwordsapp.data.model.QuizQuestionsResponse

object WordsForQuiz {
//    val listOfWords = mutableListOf(
//        QuizQuestionsResponse(3, "Car", listOf("Pişik", "Dondurma", "Şəhər", "Avtobus")),
//        QuizQuestionsResponse(3, "Ball", listOf("Qələm", "Kitab", "Dəftər", "Top")),
//        QuizQuestionsResponse(2, "Cat", listOf("Qapı", "Çanta", "Pişik", "Fincan")),
//        QuizQuestionsResponse(0, "Dog", listOf("İt", "Yol", "Stol", "Qar")),
//        QuizQuestionsResponse(1, "Elephant", listOf("Zurafə", "Fil", "Gül", "Qapı")),
//        QuizQuestionsResponse(3, "Fish", listOf("Stol", "Qələm", "Meyvə", "Balıq")),
//        QuizQuestionsResponse(0, "Green", listOf("Yaşıl", "Qırmızı", "Sarı", "Mavi")),
//        QuizQuestionsResponse(2, "House", listOf("At", "Kitab", "Ev", "Qapı")),
//        QuizQuestionsResponse(1, "Ice", listOf("Kitab", "Buz", "Ağac", "Dondurma")),
//        QuizQuestionsResponse(3, "Juice", listOf("Lampa", "Masa", "Çanta", "Şirə")),
//        QuizQuestionsResponse(0, "Kite", listOf("Uçurtma", "Pişik", "Balıq", "Kitab")),
//        QuizQuestionsResponse(2, "Lemon", listOf("Hand", "Stol", "Limon", "Dəftər")),
//        QuizQuestionsResponse(1, "Milk", listOf("Qapı", "Süd", "Telefon", "İt")),
//        QuizQuestionsResponse(0, "Night", listOf("Gecə", "Günəş", "Yumurta", "Qələm")),
//        QuizQuestionsResponse(3, "Orange", listOf("Mavi", "Limon", "Fil", "Portağal")),
//        QuizQuestionsResponse(0, "Pencil", listOf("Qələm", "Qar", "Günəş", "Top")),
//        QuizQuestionsResponse(2, "Queen", listOf("İt", "Qapı", "Kraliça", "Buz")),
//        QuizQuestionsResponse(1, "Red", listOf("Masa", "Qırmızı", "Sıra", "Telefon")),
//        QuizQuestionsResponse(2, "Apple", listOf("Şagird", "Armud", "Alma", "Ay")),
//        QuizQuestionsResponse(0, "Book", listOf("Kitab", "Çanta", "Yaşıl", "Telefon")),
//    )

//    val listOfElementaryWords = listOf(
//        QuizQuestionsResponse(0, "Airport", listOf("Hava Limanı", "Mağaza", "Kitab", "Meyvə")),
//        QuizQuestionsResponse(1, "Animal", listOf("Telefon", "Heyvan", "Çanta", "Gül")),
//        QuizQuestionsResponse(2, "Answer", listOf("Sual", "Fil", "Cavab", "Qələm")),
//        QuizQuestionsResponse(3, "Autumn", listOf("Çiçək", "Yol", "Qar", "Payız")),
//        QuizQuestionsResponse(0, "Beautiful", listOf("Gözəl", "İnsan", "Ev", "İt")),
//        QuizQuestionsResponse(1, "Bicycle", listOf("Qapı", "Velosiped", "Balıq", "Sandıq")),
//        QuizQuestionsResponse(2, "Birthday", listOf("Qələmlik", "Uçurtma", "Doğum günü", "Çay")),
//        QuizQuestionsResponse(3, "Breakfast", listOf("Qırmızı", "Pişik", "Balıq", "Səhər yeməyi")),
//        QuizQuestionsResponse(0, "Building", listOf("Bina", "Telefon", "Gül", "Dağ")),
//        QuizQuestionsResponse(1, "Butterfly", listOf("İt", "Kəpənək", "Velosiped", "Dəftər")),
//        QuizQuestionsResponse(2, "Ship", listOf("Ev", "Məktəb", "Gəmi", "Qələm")),
//        QuizQuestionsResponse(3, "Winter", listOf("Yay", "Kitab", "Balıq", "Qış")),
//        QuizQuestionsResponse(0, "Cheese", listOf("Pendir", "Yağ", "Qələm", "Buz")),
//        QuizQuestionsResponse(1, "Chicken", listOf("Qələm", "Toyuq", "Kitab", "Yumurta")),
//        QuizQuestionsResponse(2, "Circle", listOf("Ulduz", "Yol", "Dairə", "Sandıq")),
//        QuizQuestionsResponse(3, "Clothes", listOf("Pişik", "Qələm", "Dəftər", "Geyim")),
//        QuizQuestionsResponse(0, "Coffee", listOf("Qəhvə", "Yol", "Restoran", "Sandıq")),
//        QuizQuestionsResponse(1, "Country", listOf("Kitab", "Ölkə", "Şəhər", "Qitə")),
//        QuizQuestionsResponse(2, "Cow", listOf("Çiçək", "Bayquş", "İnək", "Qoyun")),
//        QuizQuestionsResponse(3, "Crowded", listOf("Qar", "Velosiped", "Ağac", "İzdiham")),
//        QuizQuestionsResponse(0, "Dangerous", listOf("Təhlükəli", "Asan", "Rahat", "Böyük")),
//        QuizQuestionsResponse(1, "Daughter", listOf("Ana", "Qız", "Nənə", "Nəvə")),
//        QuizQuestionsResponse(2, "Different", listOf("Ev", "Eyni", "Fərqli", "Kiçik")),
//        QuizQuestionsResponse(3, "Dinner", listOf("Toyuq", "Uçurtma", "Çay", "Şam yeməyi")),
//        QuizQuestionsResponse(0, "Doctor", listOf("Həkim", "Polis", "Hakim", "Meyvə")),
//        QuizQuestionsResponse(1, "Money", listOf("Velosiped", "Pul", "Məktəb", "Kitab")),
//        QuizQuestionsResponse(2, "Giraffe", listOf("Gəmi", "Telefon", "Zürafə", "Çiçək")),
//        QuizQuestionsResponse(3, "Evening", listOf("Pişik", "Dəftər", "Səhər", "Axşam")),
//        QuizQuestionsResponse(0, "Excited", listOf("Həyəcanlı", "Qorxulu", "Balıq", "Gözəl")),
//        QuizQuestionsResponse(1, "Expensive", listOf("Ucuz", "Bahalı", "Çanta", "Ev")),
//        QuizQuestionsResponse(2, "Family", listOf("Velosiped", "Qapı", "Ailə", "Soyad")),
//        QuizQuestionsResponse(3, "Surname", listOf("Kitab", "Ad", "Ağac", "Soyad")),
//        QuizQuestionsResponse(0, "Lake", listOf("Göl", "Dəniz", "Çay", "Ləkə")),
//        QuizQuestionsResponse(1, "Flower", listOf("Günəş", "Gül", "Çanta", "Yol")),
//        QuizQuestionsResponse(2, "Friend", listOf("Qapı", "Balıq", "Dost", "Çiçək")),
//        QuizQuestionsResponse(3, "Fruit", listOf("Pişik", "Gəmi", "Dəftər", "Meyvə")),
//        QuizQuestionsResponse(0, "Garden", listOf("Bağ", "Çanta", "Bulud", "Qələm")),
//        QuizQuestionsResponse(1, "Holiday", listOf("Kitab", "Tətil", "Dəniz", "Məktəb")),
//        QuizQuestionsResponse(1, "Spring", listOf("Qış", "Bahar", "Otel", "Hovuz")),
//        QuizQuestionsResponse(0, "Summer", listOf("Yay", "İsti", "Dəniz", "Payız")),
//        QuizQuestionsResponse(2, "Horse", listOf("Ev", "Bina", "At", "Məktəb")),
//        QuizQuestionsResponse(3, "Bird", listOf("Kitab", "Meymun", "Çörək", "Quş")),
//        QuizQuestionsResponse(2, "Monkey", listOf("Pul", "Dağ", "Meymun", "Ay")),
//        QuizQuestionsResponse(3, "Hand", listOf("Ayaq", "Bel", "Barmaq", "Əl")),
//        QuizQuestionsResponse(0, "Finger", listOf("Barmaq", "Ayaq", "Böyük", "Fincan")),
//    )

    val listOfIntermediateWords = listOf(
        QuizQuestionsResponse(1, "Community", listOf("İlişki", "Toplum", "Detal", "Qismət")),
        QuizQuestionsResponse(1, "Random", listOf("Rəqəm", "Təsadüfi", "Yaranmaq", "Təkrar")),
        QuizQuestionsResponse(0, "Range", listOf("Aralıq", "Hərəkət", "Nüfuz", "Söz")),
        QuizQuestionsResponse(3, "Reinforce", listOf("Yağış", "Nəfər", "Yaranmaq", "Təqviyə")),
        QuizQuestionsResponse(0, "Reject", listOf("Ləğv", "Xəbər", "Yaranmaq", "Saxlamaq")),
        QuizQuestionsResponse(1, "Release", listOf("Yaratmaq", "Buraxmaq", "Təkrar", "Söz")),
        QuizQuestionsResponse(2, "Reliable", listOf("Davamlı", "Mürəkkəb", "Etibarlı", "Sadə")),
        QuizQuestionsResponse(3, "Resident", listOf("Müvəqqəti", "Xəbər", "Prezident", "Sakin")),
        QuizQuestionsResponse(0, "Restrain", listOf("Saxlamaq", "Buraxmaq", "Qaldırmaq", "Əsdirmək")),
        QuizQuestionsResponse(1, "Restrict", listOf("Xəbərdarlıq", "Məhdudlaşdırmaq", "Yay", "İzn")),
        QuizQuestionsResponse(2, "Reveal", listOf("Saxlı", "Xəbər", "Aşkar", "Gizli")),
        QuizQuestionsResponse(3, "Visdom", listOf("Məzmun", "Savadlı", "Alicənab", "Müdrik")),
        QuizQuestionsResponse(0, "Simulate", listOf("Təqlid", "Ağlamaq", "Böyütmək", "Gecikmək")),
        QuizQuestionsResponse(1, "Solar", listOf("Fərq", "Günəşli", "Aylı", "Küləkli")),
        QuizQuestionsResponse(2, "Specify", listOf("İtirmək", "Silmək", "Müəyyənləşdirmək", "Tamamlamaq")),
        QuizQuestionsResponse(3, "Dot", listOf("Planlaşdırmaq", "Boşluq", "Vergül", "Nöqtə")),
        QuizQuestionsResponse(0, "Reverse", listOf("Tərs", "Yuxarı", "Əyri", "Düz")),
        QuizQuestionsResponse(1, "Straight", listOf("Əyri", "Düz", "Aşağı", "Böyük")),
        QuizQuestionsResponse(2, "Injury", listOf("Əvəz etmək", "Dəyişmək", "Zədə", "Sağalmaq")),
        QuizQuestionsResponse(3, "Suspend", listOf("Gözləmək", "Əymək", "Davam", "Dayandırmaq")),
        QuizQuestionsResponse(0, "Universe", listOf("Kainat", "Planet", "Ulduz", "Ay")),
        QuizQuestionsResponse(1, "Target", listOf("Dairə", "Hədəf", "Götürmək", "Alçaq")),
        QuizQuestionsResponse(2, "Terminate", listOf("Qətl", "Başlamaq", "Bitirmək", "Dondurmaq")),
        QuizQuestionsResponse(3, "Theme", listOf("Temizlik", "Kağız", "Başlıq", "Mövzu")),
        QuizQuestionsResponse(2, "Transition", listOf("Qorxu", "Qaçmaq", "Keçid", "Susmaq")),
        QuizQuestionsResponse(0, "Transparent", listOf("Şəffaf", "Tədbir", "Valideyin", "Qara")),
        QuizQuestionsResponse(1, "Trend", listOf("İrəliləyiş", "Ənənəvi", "Köhnə", "Digər")),
        QuizQuestionsResponse(2, "Equal", listOf("Sərbəst", "Ekvator", "Bərabər", "Ayrı")),
        QuizQuestionsResponse(3, "Utilize", listOf("Bitirmək", "Bayram", "Yarılamaq", "İstifadə etmək")),
        QuizQuestionsResponse(0, "Vehicle", listOf("Nəqliyyat", "Gümüş", "Yaş", "Torpaq")),
        QuizQuestionsResponse(1, "Version", listOf("Növ", "Versiya", "Növbəti", "Qısa")),
        QuizQuestionsResponse(2, "Via", listOf("Arxasında", "Ətraf", "Vasitəsilə", "Məhdud")),
        QuizQuestionsResponse(0, "Vision", listOf("Görmə", "Vasitə", "Aralıq", "Ortaq")),
        QuizQuestionsResponse(1, "Volume", listOf("Xırda", "Həcm", "Çəki", "En")),
        QuizQuestionsResponse(2, "Whereas", listOf("Hələki", "Lakin", "Halbuki", "Harada")),
        QuizQuestionsResponse(3, "Widespread", listOf("Qısacası", "Ətrafda", "Daxildə", "Geniş yayılmış")),
        QuizQuestionsResponse(0, "Yield", listOf("Məhsul", "Tərəvəz", "Gözlük", "Qara")),
        QuizQuestionsResponse(1, "Abstract", listOf("Yeganə", "Mücərrəd", "Tək", "Çoxluq")),
        QuizQuestionsResponse(2, "Accumulate", listOf("Ayırmaq", "Bölmək", "Toplamaq", "Yığışdırmaq")),
        QuizQuestionsResponse(1, "Append", listOf("Silmək", "Əlavə etmək", "Çözmək", "Sarımaq")),
        QuizQuestionsResponse(3, "Automate", listOf("Əvəzləmək", "Təmir etmək", "Anlamaq", "Avtomatlaşdırmaq")),
        QuizQuestionsResponse(0, "Bias", listOf("Qərəzlik", "Yumuşaq", "Mehriban", "Kobud")),
        QuizQuestionsResponse(2, "Bulk", listOf("Böyük", "Kiçik", "Toplu", "Həcm")),
        QuizQuestionsResponse(3, "Cease", listOf("Kəsmək", "Dondurmaq", "Davam etdirmək", "Dayandırmaq")),
        QuizQuestionsResponse(1, "Concurrent", listOf("Eyni", "Paralel", "Rəqib", "Düşmən")),
        QuizQuestionsResponse(0, "Confirm", listOf("Təstiq etmək", "İcazə", "İcazə Almaq", "Yetgin")),
        QuizQuestionsResponse(1, "Contemporary", listOf("Mövcud", "Müasir", "Fərqli", "Qara")),
        QuizQuestionsResponse(2, "Contradiction", listOf("Qəti", "Eyni", "Ziddiyyət", "Mübahisə")),
        QuizQuestionsResponse(1, "Converse", listOf("Əks", "Müzakirə", "Hündür", "Razılaşmaq")),
        QuizQuestionsResponse(2, "Convey", listOf("Bölmək", "Geri almaq", "Ötürmək", "Yandırmaq")),
        QuizQuestionsResponse(0, "Defect", listOf("Qüsur", "Tam", "Zərər", "Fərdi")),
        QuizQuestionsResponse(1, "Deficiency", listOf("Nöqsan", "Çatışmamazlıq", "Yarım", "Qısa")),
        QuizQuestionsResponse(3, "Devise", listOf("Yetiştirmək", "Böyütmək", "Silmək", "Hazırlamaq")),
        QuizQuestionsResponse(2, "Destroy", listOf("Əzmək", "Tədbir", "Dağıtmaq", "Sıxmaq")),
        QuizQuestionsResponse(3, "Duration", listOf("Yarım", "Bölgü", "Bütov", "Davametmə")),
        QuizQuestionsResponse(0, "Define", listOf("Müəyyənləşdirmək", "İtirmək", "Qaçırtmaq", "Qalmaq")),
        QuizQuestionsResponse(3, "Excellent", listOf("Pis", "Yaxşı", "Kafi", "Möhtəşəm")),
        QuizQuestionsResponse(0, "Forgot", listOf("Unutmaq", "Razılaşmaq", "İmtina", "Böyümək")),
        QuizQuestionsResponse(1, "Frost", listOf("Buz", "Şaxta", "Soyuducu", "Dondurma")),
        QuizQuestionsResponse(2, "Framework", listOf("Qutu", "Masa", "Çərçivə", "Ayna")),
        QuizQuestionsResponse(3, "Guideline", listOf("Göstəriş", "İtkin", "Ağır", "Təlimat")),
        QuizQuestionsResponse(0, "Hero", listOf("Qəhrəman", "Vergi", "Döyüşçü", "Əsgər")),
        QuizQuestionsResponse(1, "Hypothesis", listOf("Fakt", "Fərziyyə", "İnanc", "Müxtəlif")),
        QuizQuestionsResponse(3, "Immortal", listOf("Sonsuz", "Tək", "Böyük", "Ölümsüz")),
        QuizQuestionsResponse(0, "Inspiration", listOf("Ilham", "Təsəlli", "Qərar", "Yekun")),
        QuizQuestionsResponse(2, "Infinity", listOf("Sadəlik", "Avtomobil", "Sonsuzluq", "Səma")),
        QuizQuestionsResponse(2, "Isolate", listOf("Birləşdirmək", "Tədbir", "Təcrid", "Toplamaq")),
        QuizQuestionsResponse(3, "Layer", listOf("Sütun", "Bölgü", "Kəlmə", "Qat")),
        QuizQuestionsResponse(0, "Medium", listOf("Orta", "Kiçik", "Böyük", "Ara")),
        QuizQuestionsResponse(1, "Walk", listOf("Divar", "Gəzmək", "Qaçmaq", "Uçmaq")),
        QuizQuestionsResponse(2, "Swim", listOf("Uçmaq", "Sürünmək", "Üzmək", "Gəzmək")),
        QuizQuestionsResponse(3, "Wall", listOf("Gəzmək", "Zirzəmi", "Qapı", "Divar")),
        QuizQuestionsResponse(3, "Talk", listOf("Susmaq", "Dindirmək", "Dinləmək", "Danışmaq")),
        QuizQuestionsResponse(0, "Speak", listOf("Danışmaq", "Dindirmək", "Dinləmək", "Susmaq")),
        QuizQuestionsResponse(1, "Listen", listOf("Danışmaq", "Dinləmək", "Səssizlik", "Susmaq")),
        QuizQuestionsResponse(2, "Write", listOf("Başlamaq", "Dindirmək", "Yazmaq", "Sarımaq")),
        QuizQuestionsResponse(2, "Understand", listOf("Başlamaq", "Bilməmək", "Anlamaq", "Bitirmək")),
    )
}