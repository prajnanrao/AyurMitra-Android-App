package com.example.ayurmitra.utils;

import com.example.ayurmitra.R;
import com.example.ayurmitra.models.Dosha;
import com.example.ayurmitra.models.Expert;
import com.example.ayurmitra.models.Herb;
import com.example.ayurmitra.models.Yoga;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    public static List<Herb> getHerbs() {
        List<Herb> herbs = new ArrayList<>();

        herbs.add(new Herb(
                "Ashwagandha",
                R.drawable.ashwagandha,
                "Ancient Ayurvedic herb used for stress relief.",
                "The King of Ayurvedic Herbs 🌿",
                "• Reduces stress and anxiety\n• Improves strength and stamina\n• Boosts immunity and brain function",
                "Take 1/2 teaspoon of Ashwagandha powder with warm milk or water twice a day after meals.",
                "Avoid if pregnant or breastfeeding. Consult a doctor if you have autoimmune diseases.",
                "Vata & Kapha"
        ));

        herbs.add(new Herb(
                "Tulsi (Holy Basil)",
                R.drawable.tulsi,
                "Sacred herb known for immunity boosting.",
                "The Queen of Herbs 👑",
                "• Natural immunity booster\n• Helps in respiratory disorders\n• Reduces stress and blood pressure",
                "Chew 3-4 fresh leaves daily or drink Tulsi tea by boiling leaves in water.",
                "May lower blood sugar levels; use with caution if taking diabetes medication.",
                "Vata & Kapha"
        ));

        herbs.add(new Herb(
                "Amla",
                R.drawable.amla,
                "Rich source of Vitamin C.",
                "The Ultimate Superfood 🍎",
                "• High in Vitamin C and antioxidants\n• Improves digestion and metabolism\n• Excellent for skin and hair health",
                "Consume 1 fresh Amla daily or take 2 teaspoons of Amla juice with water on an empty stomach.",
                "Avoid excessive consumption if you have blood thinning disorders.",
                "Pitta"
        ));

        herbs.add(new Herb(
                "Neem",
                R.drawable.neem,
                "Powerful detox herb.",
                "Nature's Pharmacy 🍃",
                "• Purifies blood and detoxifies the body\n• Treats acne and skin infections\n• Anti-fungal and anti-bacterial properties",
                "Apply Neem paste on skin or take 1-2 Neem tablets daily as prescribed.",
                "Not recommended for pregnant women or those trying to conceive.",
                "Pitta & Kapha"
        ));

        herbs.add(new Herb(
                "Turmeric",
                R.drawable.turmeric,
                "Golden anti-inflammatory spice.",
                "The Golden Healer ✨",
                "• Potent anti-inflammatory and antioxidant\n• Heals wounds and improves skin health\n• Supports joint and brain health",
                "Add 1/2 teaspoon to warm milk (Golden Milk) or use in daily cooking.",
                "High doses may cause stomach upset. Caution for those with gallstones.",
                "All Doshas"
        ));

        herbs.add(new Herb(
                "Brahmi",
                R.drawable.brahmi,
                "Brain tonic herb.",
                "The Memory Enhancer 🧠",
                "• Boosts memory and concentration\n• Reduces anxiety and mental fatigue\n• Promotes sound sleep",
                "Take 1/2 teaspoon Brahmi powder with honey or ghee, or as a tablet.",
                "May cause mild stomach upset in some individuals.",
                "Pitta & Vata"
        ));

        herbs.add(new Herb(
                "Giloy",
                R.drawable.giloy,
                "Immunity booster vine.",
                "Root of Immortality ♾️",
                "• Fights chronic fevers and infections\n• Improves digestion and bowel health\n• Helps manage diabetes",
                "Drink 15-20ml of Giloy juice mixed with water daily.",
                "May overstimulate the immune system; avoid if you have autoimmune diseases.",
                "All Doshas"
        ));

        herbs.add(new Herb(
                "Shatavari",
                R.drawable.shatavari,
                "Women's health herb.",
                "Woman with 100 Husbands 🌸",
                "• Balances female hormones\n• Improves fertility and vitality\n• Supports lactation in new mothers",
                "Take 1 teaspoon with warm milk at night.",
                "Avoid if you have estrogen-sensitive conditions.",
                "Pitta & Vata"
        ));

        herbs.add(new Herb(
                "Ginger",
                R.drawable.ginger,
                "Digestive root.",
                "The Universal Medicine 🫚",
                "• Relief from nausea and indigestion\n• Reduces muscle pain and soreness\n• Fights common cold and flu",
                "Drink ginger tea or chew a small piece of fresh ginger with a pinch of salt before meals.",
                "Avoid excessive use if you have heart conditions or gallstones.",
                "Vata & Kapha"
        ));

        herbs.add(new Herb(
                "Mulethi",
                R.drawable.mulethi,
                "Sweet root herb.",
                "The Soothing Root 🍯",
                "• Soothes sore throat and cough\n• Reduces acidity and stomach ulcers\n• Protects the liver",
                "Chew a small piece of licorice root or drink Mulethi tea.",
                "Not suitable for people with high blood pressure or kidney disease.",
                "Pitta & Vata"
        ));

        return herbs;
    }

    public static List<Expert> getExperts() {
        List<Expert> experts = new ArrayList<>();

        experts.add(new Expert(
                "Dr. Partap Chauhan",
                "Nadi Pariksha Expert",
                "30+ Years",
                R.drawable.dr_partap,
                "Founder of Jiva Ayurveda, a global pioneer in Tele-Ayurveda. He has dedicated his life to reviving ancient Ayurvedic wisdom for modern health challenges.",
                "BAMS, Silver Medalist (Delhi University)",
                "₹500",
                "Mon - Sat (10 AM - 6 PM)",
                "4.9",
                "https://www.jiva.com",
                "+911294040404",
                "info@jiva.com",
                "Vata"
        ));

        experts.add(new Expert(
                "Dr. Smita Naram",
                "Women's Health Expert",
                "30+ Years",
                R.drawable.dr_smita,
                "Co-founder of Ayushakti. She has helped over a million people worldwide through her deep research in herbal formulas and pulse reading.",
                "BAMS, PhD in Ayurveda",
                "₹800",
                "Tue - Sun (11 AM - 7 PM)",
                "4.8",
                "https://www.ayushakti.com",
                "+912228065757",
                "contact@ayushakti.com",
                "Pitta"
        ));

        experts.add(new Expert(
                "Dr. Ramkumar Kutty",
                "Panchakarma Specialist",
                "25+ Years",
                R.drawable.dr_ramkumar,
                "Founder of Vaidyagrama Healing Village. He specializes in traditional Kerala Panchakarma and sustainable Ayurvedic living.",
                "BAMS from Madras University",
                "₹1200",
                "By Appointment Only",
                "4.9",
                "https://www.vaidyagrama.com",
                "+919488433331",
                "om@vaidyagrama.com",
                "Kapha"
        ));

        experts.add(new Expert(
                "Dr. Pankaj Naram",
                "Nadi Pariksha Specialist",
                "35+ Years",
                R.drawable.dr_pankaj,
                "World-renowned master of ancient healing secrets. He was famous for his ability to diagnose health issues using only three fingers on a pulse.",
                "BAMS, Master of Siddha-Veda",
                "₹1000",
                "Available for Consultations",
                "4.9",
                "https://www.drnaram.com",
                "",
                "info@drnaram.com",
                "Vata"
        ));

        experts.add(new Expert(
                "Dr. G.G. Gangadharan",
                "Ayurveda Research Expert",
                "40+ Years",
                R.drawable.dr_gangadharan,
                "Director at Ramaiah Indic Specialty Ayurveda Restoration Hospital. He focuses on integrating Ayurveda with mainstream medicine.",
                "BAMS, FAIM (USA)",
                "₹600",
                "Wed - Sat (9 AM - 1 PM)",
                "4.7",
                "https://www.ramaiahayurveda.com",
                "+918022183456",
                "drgg@ramaiahIndic.com",
                "All"
        ));

        return experts;
    }

    public static List<Expert> getRecommendedExperts(String userDosha) {
        List<Expert> allExperts = getExperts();
        List<Expert> recommended = new ArrayList<>();
        if (userDosha == null) return recommended;
        
        for (Expert expert : allExperts) {
            if (expert.getExpertiseDosha().equalsIgnoreCase(userDosha) || expert.getExpertiseDosha().equalsIgnoreCase("All")) {
                recommended.add(expert);
            }
        }
        return recommended;
    }

    public static List<Dosha> getDoshas() {
        List<Dosha> doshas = new ArrayList<>();

        doshas.add(new Dosha(
                "Vata",
                "The Energy of Movement 🌬️",
                "Vata is composed of space and air. It governs all biological activity, including breathing, heart pulsation, and nerve impulses.",
                R.drawable.vata,
                "• Light, cold, dry, and rough nature\n• Quick in thought and action\n• Creative and enthusiastic when in balance",
                "• Dry skin and cold extremities\n• Anxiety, fear, and restlessness\n• Insomnia and irregular digestion",
                "• Warm, cooked, and grounding foods\n• Sweet, sour, and salty tastes\n• Healthy fats and oils (Ghee, Sesame oil)",
                "• Maintain a regular daily routine\n• Practice calming meditation and gentle yoga\n• Stay warm and get plenty of rest"
        ));

        doshas.add(new Dosha(
                "Pitta",
                "The Energy of Digestion 🔥",
                "Pitta is composed of fire and water. It governs digestion, absorption, assimilation, nutrition, metabolism, and body temperature.",
                R.drawable.pitta,
                "• Hot, sharp, light, and oily nature\n• Strong intellect and determination\n• Natural leaders with a competitive edge",
                "• Excessive body heat and inflammation\n• Irritability, anger, and impatience\n• Acid reflux and skin rashes",
                "• Cooling and refreshing foods\n• Sweet, bitter, and astringent tastes\n• Fresh fruits (melons, pears) and leafy greens",
                "• Avoid excessive heat and midday sun\n• Practice moderation and patience\n• Engage in calming, non-competitive activities"
        ));

        doshas.add(new Dosha(
                "Kapha",
                "The Energy of Structure 🌿",
                "Kapha is composed of earth and water. It provides the structure for the body and governs the lubrication of joints and lungs.",
                R.drawable.kapha,
                "• Heavy, slow, steady, and cold nature\n• Calm, loving, and forgiving personality\n• Strong stamina and healthy immune system",
                "• Weight gain and lethargy\n• Congestion and respiratory issues\n• Attachment and resistance to change",
                "• Light, warm, and spicy foods\n• Bitter, pungent, and astringent tastes\n• Plenty of fresh vegetables and legumes",
                "• Exercise regularly and stay active\n• Seek new experiences and variety\n• Avoid sleeping during the day"
        ));

        return doshas;
    }

    public static List<Yoga> getYogaList() {
        List<Yoga> yogaList = new ArrayList<>();

        // --- CORE PHILOSOPHY & KNOWLEDGE ---
        yogaList.add(new Yoga(
                "8 Limbs of Yoga (Ashtanga)", R.drawable.eight_limbs,
                "The complete structural framework for yoga practice according to Sage Patanjali.",
                "• Provides a roadmap for ethical living and self-discipline\n• Harmonizes the body, mind, and spirit\n• Leads to higher states of consciousness and inner peace",
                "1. Yama (Moral codes)\n2. Niyama (Personal discipline)\n3. Asana (Physical postures)\n4. Pranayama (Breath control)\n5. Pratyahara (Sense withdrawal)\n6. Dharana (Concentration)\n7. Dhyana (Meditation)\n8. Samadhi (Union)",
                "Study each limb deeply and practice under a certified teacher.",
                "8 limbs of yoga philosophy", "Philosophy", "All"
        ));

        yogaList.add(new Yoga(
                "Yoga for Modern Lifestyle", R.drawable.yoga,
                "A collection of tips to integrate yoga into a busy daily routine.",
                "• Reduces stress from desk jobs and screens\n• Improves spinal health and flexibility\n• Increases daily energy levels",
                "1. Practice 10 mins of Surya Namaskar daily.\n2. Do desk stretches every 2 hours.\n3. Take 5 mins for Anulom Vilom before sleep.\n4. Practice mindfulness during meals.",
                "Consistency is more important than duration.",
                "Yoga for stress and office workers", "Yoga Tips", "All"
        ));

        // --- ASANAS ---
        yogaList.add(new Yoga(
                "Surya Namaskar (Sun Salutation)", R.drawable.sn,
                "A dynamic 12-step sequence representing the solar energy.",
                "• Improves blood circulation\n• Enhances flexibility\n• Aids in healthy weight loss",
                "1. Prayer pose\n2. Raised arms\n3. Hand to foot\n4. Equestrian pose\n5. Stick pose\n6. Salute with 8 points\n7. Cobra pose\n8. Downward dog\n9. Equestrian pose\n10. Hand to foot\n11. Raised arms\n12. Mountain pose",
                "Avoid if suffering from high BP or hernia.",
                "Surya Namaskar tutorial steps", "Asana", "All"
        ));

        yogaList.add(new Yoga(
                "Tadasana (Mountain Pose)", R.drawable.y2,
                "The foundational standing pose for better posture.",
                "• Corrects posture and balance\n• Strengthens knees and ankles\n• Relieves sciatica",
                "1. Stand tall with feet together.\n2. Engage your thighs.\n3. Lengthen your spine.\n4. Arms at your side, gaze forward.",
                "Caution with low BP or headaches.",
                "Tadasana yoga guide", "Asana", "All"
        ));

        yogaList.add(new Yoga(
                "Vrikshasana (Tree Pose)", R.drawable.y3,
                "A balancing pose for mental stability.",
                "• Improves balance and concentration\n• Strengthens legs and core\n• Calms the mind",
                "1. Stand on one leg.\n2. Place other foot on inner thigh.\n3. Hands in prayer position at chest.",
                "Avoid with high BP or insomnia.",
                "Tree pose yoga tutorial", "Asana", "All"
        ));

        yogaList.add(new Yoga(
                "Bhujangasana (Cobra Pose)", R.drawable.y4,
                "A gentle back-bend to strengthen the spine.",
                "• Strengthens the spine\n• Opens chest and lungs\n• Stimulates abdominal organs",
                "1. Lie on stomach.\n2. Hands under shoulders.\n3. Lift chest off floor while keeping pelvis down.",
                "Avoid with back injury or pregnancy.",
                "Cobra pose tutorial", "Asana", "Pitta"
        ));

        yogaList.add(new Yoga(
                "Dhanurasana (Bow Pose)", R.drawable.y5,
                "Intense back-bend resembling an archer's bow.",
                "• Improves digestion and appetite\n• Strengthens back and core\n• Relieves stress",
                "1. Lie on stomach.\n2. Bend knees and hold ankles.\n3. Pull chest and thighs up.",
                "Avoid with hernia or high BP.",
                "Dhanurasana bow pose", "Asana", "Kapha"
        ));

        yogaList.add(new Yoga(
                "Paschimottanasana", R.drawable.pan,
                "Calming seated forward bend.",
                "• Stretches spine and hamstrings\n• Calms the brain and relieves stress\n• Improves digestion",
                "1. Sit with legs stretched out.\n2. Reach forward and touch toes.\n3. Keep back as straight as possible.",
                "Avoid with asthma or slipped disc.",
                "Paschimottanasana guide", "Asana", "Vata"
        ));

        yogaList.add(new Yoga(
                "Adho Mukha Svanasana", R.drawable.y7,
                "Downward-facing dog for rejuvenation.",
                "• Energizes the body and relieves fatigue\n• Stretches shoulders and calves\n• Relieves headache",
                "1. Start on hands and knees.\n2. Lift hips up to form inverted V.\n3. Relax head between arms.",
                "Avoid with carpal tunnel syndrome.",
                "Downward dog pose yoga", "Asana", "All"
        ));

        yogaList.add(new Yoga(
                "Vajrasana (Thunderbolt)", R.drawable.y8,
                "The only pose recommended after meals for digestion.",
                "• Enhances digestion and relieves gas\n• Strengthens pelvic muscles\n• Calms the mind",
                "1. Kneel and sit on your heels.\n2. Keep spine straight, hands on knees.",
                "Avoid with acute knee injury.",
                "Vajrasana for digestion", "Asana" , "All"
        ));

        yogaList.add(new Yoga(
                "Halasana (Plow Pose)", R.drawable.y9,
                "Deep stretch for the spine and neck.",
                "• Calms the brain and reduces stress\n• Stimulates thyroid gland\n• Stretches spine deeply",
                "1. Lie on back.\n2. Lift legs over head until toes touch floor behind.",
                "Avoid with neck injury or high BP.",
                "Halasana plow pose", "Asana", "Kapha"
        ));

        yogaList.add(new Yoga(
                "Virabhadrasana (Warrior Pose)", R.drawable.y11,
                "Builds strength, focus, and stamina.",
                "• Strengthens arms and legs\n• Improves balance\n• Energizes body",
                "1. Step one foot back.\n2. Bend front knee.\n3. Reach arms up to the ceiling.",
                "Avoid with high BP.",
                "Warrior pose yoga", "Asana", "All"
        ));

        yogaList.add(new Yoga(
                "Trikonasana (Triangle Pose)", R.drawable.tri,
                "Standing pose for overall body stretch.",
                "• Strengthens legs and core\n• Stretches hips and spine\n• Stimulates abdominal organs",
                "1. Stand wide with feet.\n2. Reach down to touch your ankle.\n3. Lift other arm up.",
                "Avoid with low BP or back injury.",
                "Triangle pose tutorial", "Asana", "All"
        ));

        yogaList.add(new Yoga(
                "Balasana (Child's Pose)", R.drawable.bala,
                "A gentle resting pose to relax.",
                "• Gently stretches hips and thighs\n• Calms the mind and relieves stress\n• Relieves back pain",
                "1. Kneel and sit on heels.\n2. Fold forward and rest forehead on floor.",
                "Avoid with knee injury or diarrhea.",
                "Child's pose yoga", "Asana", "All"
        ));

        yogaList.add(new Yoga(
                "Natarajasana (Dancer Pose)", R.drawable.nata,
                "Elegant balancing pose.",
                "• Improves balance and posture\n• Stretches shoulders and chest\n• Strengthens legs",
                "1. Stand on one leg.\n2. Grab other foot from behind.\n3. Lean forward and lift foot.",
                "Avoid with heart issues.",
                "Dancer pose yoga", "Asana", "All"
        ));

        yogaList.add(new Yoga(
                "Bakasana (Crow Pose)", R.drawable.crow,
                "Arm balance for strength.",
                "• Strengthens arms and wrists\n• Tones core muscles\n• Improves concentration",
                "1. Squat and place hands on floor.\n2. Lean forward and lift feet.",
                "Avoid with wrist injury.",
                "Crow pose tutorial", "Asana", "All"
        ));

        yogaList.add(new Yoga(
                "Sirsasana (Headstand)", R.drawable.sira,
                "The King of Asanas for brain health.",
                "• Improves blood flow to brain\n• Strengthens neck and shoulders\n• Calms the mind",
                "1. Interlace fingers and place forearms on floor.\n2. Place head in hands and lift legs slowly.",
                "Avoid with high BP or glaucoma.",
                "Headstand yoga tutorial", "Asana", "All"
        ));

        yogaList.add(new Yoga(
                "Gomukhasana (Cow Face Pose)", R.drawable.go,
                "A seated pose that stretches the shoulders and hips.",
                "• Stretches the hips, thighs, shoulders, and chest\n• Strengthens the back muscles\n• Helps relieve sciatica",
                "1. Sit with legs extended.\n2. Fold left leg under right hip.\n3. Fold right leg over left thigh.\n4. Reach right arm up and left arm behind back to clasp fingers.",
                "Avoid with serious neck or shoulder injury.",
                "Gomukhasana cow face pose", "Asana", "Vata"
        ));

        yogaList.add(new Yoga(
                "Setu Bandhasana (Bridge Pose)", R.drawable.setu,
                "A rejuvenating backbend to open the heart.",
                "• Strengthens back, glutes, and hamstrings\n• Opens the chest and shoulders\n• Calms the brain and central nervous system",
                "1. Lie on back with knees bent and feet flat.\n2. Lift hips toward the ceiling.\n3. Interlace fingers under your back.",
                "Avoid with neck or back injury.",
                "Setu Bandhasana bridge pose", "Asana", "All"
        ));

        yogaList.add(new Yoga(
                "Janu Sirsasana", R.drawable.janu,
                "Head-to-knee forward bend.",
                "• Calms the mind and relieves mild depression\n• Stretches the spine and hamstrings\n• Stimulates liver and kidneys",
                "1. Sit with one leg extended and other foot against inner thigh.\n2. Fold forward over the extended leg.",
                "Avoid with asthma or diarrhea.",
                "Janu Sirsasana tutorial", "Asana", "Pitta"
        ));

        yogaList.add(new Yoga(
                "Matsyasana (Fish Pose)", R.drawable.fish,
                "A back-bending pose that opens the throat and chest.",
                "• Relieves tension in neck and shoulders\n• Tones the pituitary and thyroid glands\n• Expands the chest and lungs",
                "1. Lie on back.\n2. Lift chest and rest top of head on floor.\n3. Arch back while keeping lower body flat.",
                "Avoid with high BP or neck injury.",
                "Matsyasana fish pose steps", "Asana", "Kapha"
        ));

        yogaList.add(new Yoga(
                "Ustrasana (Camel Pose)", R.drawable.camel,
                "Deep back-bend from a kneeling position.",
                "• Opens the entire front of the body\n• Strengthens back muscles\n• Improves posture",
                "1. Kneel on floor.\n2. Reach back and hold your heels.\n3. Push hips forward and arch back.",
                "Avoid with high BP or migraine.",
                "Ustrasana camel pose tutorial", "Asana", "Kapha"
        ));

        yogaList.add(new Yoga(
                "Ardha Matsyendrasana", R.drawable.ardha,
                "Half Lord of the Fishes spinal twist.",
                "• Increases spinal flexibility\n• Stimulates digestive fire (Agni)\n• Massages abdominal organs",
                "1. Sit with legs extended.\n2. Cross one leg over the other.\n3. Twist torso toward the top leg.",
                "Avoid with back or spine injury.",
                "Ardha Matsyendrasana spinal twist", "Asana", "All"
        ));

        yogaList.add(new Yoga(
                "Garudasana (Eagle Pose)", R.drawable.eagle,
                "A standing balancing pose requiring focus.",
                "• Strengthens and stretches ankles and calves\n• Stretches thighs, hips, shoulders, and upper back\n• Improves concentration and balance",
                "1. Stand in Tadasana.\n2. Bend knees and wrap right leg around left.\n3. Wrap right arm under left at elbows.",
                "Avoid with knee injury.",
                "Garudasana eagle pose guide", "Asana", "Vata"
        ));

        yogaList.add(new Yoga(
                "Marjaryasana-Bitilasana (Cat-Cow)", R.drawable.catcow,
                "A gentle flow between two poses to warm up the spine.",
                "• Improves spinal flexibility and health\n• Relieves stress and calms the mind\n• Massages abdominal organs",
                "1. Start on all fours.\n2. Inhale, drop belly, look up (Cow).\n3. Exhale, round spine, chin to chest (Cat).",
                "Move within your comfort range.",
                "Cat Cow pose yoga", "Asana", "All"
        ));

        yogaList.add(new Yoga(
                "Uttanasana (Standing Forward Fold)", R.drawable.utta,
                "Deep stretch for the entire back of the body.",
                "• Calms the brain and helps relieve stress\n• Stretches hamstrings, calves, and hips\n• Reduces fatigue and anxiety",
                "1. Stand with feet together.\n2. Fold forward from the hips.\n3. Let your head hang heavy.",
                "Avoid with lower back injury.",
                "Uttanasana standing forward fold", "Asana", "Vata"
        ));

        yogaList.add(new Yoga(
                "Malasana (Yogi Squat)", R.drawable.mala,
                "A deep squat that opens the hips and lower back.",
                "• Stretches the ankles, groins, and back torso\n• Tones the abdominal muscles\n• Aids in digestion",
                "1. Squat with feet slightly wider than hips.\n2. Press elbows against inner knees.\n3. Bring hands to prayer position.",
                "Avoid with knee or ankle injury.",
                "Malasana yogi squat guide", "Asana", "Kapha"
        ));

        yogaList.add(new Yoga(
                "Shalabhasana (Locust Pose)", R.drawable.sala,
                "An entry-level backbend to strengthen the back.",
                "• Strengthens the muscles of the spine and legs\n• Improves posture\n• Stimulates abdominal organs",
                "1. Lie on stomach.\n2. Lift head, chest, arms, and legs off the floor.",
                "Avoid with serious back injury.",
                "Shalabhasana locust pose tutorial", "Asana", "Kapha"
        ));

        yogaList.add(new Yoga(
                "Navasana (Boat Pose)", R.drawable.nava,
                "A seated balance that builds core strength.",
                "• Strengthens the abdomen, hip flexors, and spine\n• Stimulates kidneys and thyroid gland\n• Improves digestion",
                "1. Sit with knees bent.\n2. Lift feet off floor and extend legs.\n3. Extend arms forward parallel to floor.",
                "Avoid with heart problems or insomnia.",
                "Navasana boat pose core", "Asana", "Kapha"
        ));

        yogaList.add(new Yoga(
                "Pawanamuktasana", R.drawable.pavana,
                "Wind-relieving pose to aid digestion.",
                "• Helps in gas release and improves digestion\n• Massages the abdominal organs\n• Relieves lower back tension",
                "1. Lie on back.\n2. Fold knees and hug them to your chest.\n3. Lift head and touch nose to knees.",
                "Avoid with high BP or neck issues.",
                "Pawanamuktasana wind relieving", "Asana", "Vata"
        ));

        yogaList.add(new Yoga(
                "Savasana (Corpse Pose)", R.drawable.shava,
                "The essential pose for final relaxation.",
                "• Calms central nervous system\n• Reduces stress and headache\n• Lowers blood pressure",
                "1. Lie flat on back.\n2. Close eyes and relax every muscle.",
                "Generally safe for all.",
                "Savasana relaxation", "Asana", "All"
        ));

        yogaList.add(new Yoga(
                "Utkatasana (Chair Pose)", R.drawable.y12,
                "A powerful pose that mimics sitting on an imaginary chair.",
                "• Strengthens the legs, ankles, and spine\n• Stretches the chest and shoulders\n• Stimulates the abdominal organs and heart",
                "1. Stand with feet together.\n2. Inhale and raise your arms above your head.\n3. Exhale and bend your knees, bringing your thighs as parallel to the floor as possible.",
                "Avoid if you have chronic knee pain or low blood pressure.",
                "Chair pose yoga benefits", "Asana", "All"
        ));

        // --- PRANAYAMAS ---
        yogaList.add(new Yoga(
                "Anulom Vilom (Nadi Shodhana)", R.drawable.anlo,
                "Alternate nostril breathing.",
                "• Balances energy\n• Reduces anxiety\n• Detoxifies the body",
                "1. Inhale left, exhale right.\n2. Inhale right, exhale left.",
                "Empty stomach only.",
                "Anulom Vilom technique", "Pranayama", "All"
        ));

        yogaList.add(new Yoga(
                "Kapalbhati", R.drawable.kapala,
                "Skull shining breath for detox.",
                "• Improves metabolism\n• Clears respiratory system\n• Energizes brain",
                "1. Forceful exhalation.\n2. Passive inhalation.",
                "Avoid with high BP or heart disease.",
                "Kapalbhati breathing", "Pranayama", "Kapha"
        ));

        yogaList.add(new Yoga(
                "Bhramari (Bee Breath)", R.drawable.brama,
                "Calming humming breath.",
                "• Relief from tension\n• Improves focus\n• Helps with insomnia",
                "1. Close ears with thumbs.\n2. Exhale while humming like a bee.",
                "Avoid with ear infections.",
                "Bhramari bee breath", "Pranayama", "All"
        ));

        yogaList.add(new Yoga(
                "Ujjayi (Ocean Breath)", R.drawable.ujja,
                "Victorious breath for heat regulation.",
                "• Soothes nervous system\n• Improves concentration\n• Deeply relaxing",
                "1. Inhale through nose with slight throat constriction.",
                "Keep throat constriction gentle.",
                "Ujjayi ocean breath", "Pranayama", "All"
        ));

        yogaList.add(new Yoga(
                "Sheetali (Cooling Breath)", R.drawable.sitali,
                "Cooling breath for heat relief.",
                "• Reduces body temperature\n• Calms anger\n• Reduces acidity",
                "1. Roll tongue into tube.\n2. Inhale through tongue tongue.",
                "Avoid in cold weather.",
                "Sheetali cooling breath", "Pranayama", "Pitta"
        ));

        yogaList.add(new Yoga(
                "Bhastrika (Bellows Breath)", R.drawable.bas,
                "A powerful energizing breath.",
                "• Increases oxygen intake\n• Generates internal heat\n• Clears the mind",
                "1. Take a deep breath in and breathe out forcefully through nose.\n2. Inhale and exhale at a fast pace.",
                "Avoid with high BP, heart issues or hernia.",
                "Bhastrika pranayama bellows breath", "Pranayama", "Kapha"
        ));

        yogaList.add(new Yoga(
                "Surya Bhedana", R.drawable.surya,
                "Right nostril breathing (Heating breath).",
                "• Increases body temperature\n• Improves digestion\n• Boosts confidence and energy",
                "1. Inhale only through the right nostril.\n2. Exhale only through the left nostril.",
                "Avoid if suffering from high BP or acidity.",
                "Surya Bhedana heating breath", "Pranayama", "Kapha"
        ));

        yogaList.add(new Yoga(
                "Chandra Bhedana", R.drawable.chandra,
                "Left nostril breathing (Cooling breath).",
                "• Lowers body temperature\n• Calms the mind and reduces stress\n• Helps in controlling high BP",
                "1. Inhale only through the left nostril.\n2. Exhale only through the right nostril.",
                "Avoid if suffering from cold or asthma.",
                "Chandra Bhedana cooling breath", "Pranayama", "Pitta"
        ));

        yogaList.add(new Yoga(
                "Udgeeth Pranayama", R.drawable.udgeet,
                "The chanting of 'Om' with controlled breathing.",
                "• Calms the mind and nervous system\n• Improves memory and concentration\n• Helps in better sleep",
                "1. Sit in a meditative pose.\n2. Inhale deeply and exhale while chanting 'OM' loudly and slowly.",
                "Focus on the sound vibration.",
                "Udgeeth pranayama om chanting", "Pranayama", "All"
        ));

        yogaList.add(new Yoga(
                "Sitkari Pranayama", R.drawable.sitkari,
                "The hissing cooling breath.",
                "• Cools the body and brain\n• Controls hunger and thirst\n• Good for dental and gum health",
                "1. Open lips, teeth touching.\n2. Inhale slowly through teeth with a hissing sound.\n3. Close mouth and exhale through nose.",
                "Avoid in cold winters.",
                "Sitkari cooling breath technique", "Pranayama", "Pitta"
        ));

        // --- MUDRAS ---
        yogaList.add(new Yoga(
                "Gyan Mudra", R.drawable.m1,
                "Mudra of Knowledge.",
                "• Memory power\n• Concentration boost",
                "1. Tip of index touches thumb.\n2. Other fingers straight.",
                "Practice during meditation.",
                "Gyan mudra benefits", "Mudra", "All"
        ));

        yogaList.add(new Yoga(
                "Vayu Mudra", R.drawable.m2,
                "Mudra for gas relief.",
                "• Relieves bloating\n• Arthritis relief",
                "1. Index finger at base of thumb.\n2. Press with thumb.",
                "Stop after relief.",
                "Vayu mudra technique", "Mudra", "Vata"
        ));

        yogaList.add(new Yoga(
                "Prana Mudra", R.drawable.m3,
                "Mudra of Life.",
                "• Immunity boost\n• Vision support\n• Reduces fatigue",
                "1. Little and ring finger touch thumb.",
                "Practice for 15 mins daily.",
                "Prana mudra tutorial", "Mudra", "All"
        ));

        yogaList.add(new Yoga(
                "Apana Mudra", R.drawable.m4,
                "Mudra of Digestion.",
                "• Improves digestion\n• Helps in detoxification\n• Relieves constipation",
                "1. Touch the tips of middle and ring fingers to the tip of thumb.",
                "Practice for 15-45 mins daily.",
                "Apana mudra for digestion", "Mudra", "Kapha"
        ));

        yogaList.add(new Yoga(
                "Shunya Mudra", R.drawable.m5,
                "Mudra of Emptiness (for ear issues).",
                "• Relieves earaches and numbness\n• Helps with vertigo and motion sickness",
                "1. Bend the middle finger and press it with thumb base.",
                "Stop practicing once ear issues are gone.",
                "Shunya mudra ear pain relief", "Mudra", "Vata"
        ));


        yogaList.add(new Yoga(
                "Varuna Mudra", R.drawable.m6,
                "Mudra of Water.",
                "• Balances water content in the body\n• Relieves skin dryness and blood disorders\n• Improves skin glow",
                "1. Touch the tip of little finger to the tip of thumb.",
                "Practice daily for 15 minutes.",
                "Varuna mudra skin health", "Mudra", "Pitta"
        ));

        yogaList.add(new Yoga(
                "Prithvi Mudra", R.drawable.m7,
                "Mudra of Earth.",
                "• Increases vitality and physical strength\n• Helps in healthy weight gain\n• Boosts confidence",
                "1. Touch the tip of the ring finger with the tip of the thumb.",
                "Practice for 30-45 minutes daily.",
                "Prithvi mudra physical strength", "Mudra", "Vata"
        ));

        yogaList.add(new Yoga(
                "Linga Mudra", R.drawable.m8,
                "Mudra of Heat.",
                "• Generates internal heat\n• Helps with cold and cough\n• Improves respiratory health",
                "1. Interlock both hands, keeping the left thumb upright.",
                "Drink plenty of water when practicing this mudra.",
                "Linga mudra heat benefits", "Mudra", "Kapha"
        ));

        yogaList.add(new Yoga(
                "Hridaya Mudra", R.drawable.m9,
                "Heart Mudra (Mritya-Sanjivini Mudra).",
                "• Strengthens the heart\n• Helps in regulating blood pressure\n• Reduces emotional stress",
                "1. Fold index finger to base of thumb.\n2. Touch middle and ring fingers to thumb tip.",
                "Practice during meditation for heart health.",
                "Hridaya mudra heart health", "Mudra", "Pitta"
        ));

        return yogaList;
    }
}
