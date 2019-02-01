(ns clj-deconstruct-sigs.data.cosmic
  (:require [clojure.core.matrix :as m]))

(def ^:const cosmic-signatures [[0.011098326166 0.009149340734 0.001490070468 0.006233885236 0.001801068369 0.00258090852 5.92548022E-4 0.002963986287 0.029514532745 0.014322747041 0.171646931305 0.012623763151 0.004021520333 0.002371144163 0.002810909959 0.008360909345 0.01391577303 0.0062749606 0.010137636154 0.009256316389 0.001587636423 0.001784091288 0.001385830552 0.003158539312 0.006595870109 0.007342367815 8.9284037E-4 0.007186581642 0.001284983446 7.02134818E-4 5.06289594E-4 0.001381542722 0.020896446965 0.01850170477 0.095577217268 0.017113307642 0.001182587416 0.001903166857 0.00148796063 0.002179344412 0.004176674882 0.005252593331 0.00701322531 0.006713813119 3.02691186E-4 0.00209850244 0.0015995485 0.002758537619 0.008232603989 0.00575802141 6.16335232E-4 0.004459080311 6.02122711E-4 0.002393352209 2.48534E-7 8.90080731E-4 0.024943814154 0.027161494035 0.103570762296 0.017689854381 6.89289439E-4 5.52409528E-4 0.001200228847 0.002107136837 0.011247835116 0.006999724257 0.004977592617 0.010667406133 9.9045003E-5 2.02365646E-4 0.001188353185 8.00723342E-4 0.012250063666 0.011162229329 0.002275495686 0.015259102491 0.001874853199 0.002067418791 3.04897004E-4 0.00315157446 0.014492099634 0.017680775357 0.076002221712 0.013761704021 0.005600155423 0.00199907926 0.001090065693 0.003981022761 0.008073616351 0.004857381178 0.008325454207 0.006257105605 0.001397553749 0.001291736985 0.00203107688 0.00403012816]
                                [6.82708227E-4 6.19107232E-4 9.9278956E-5 3.23891363E-4 2.63481044E-4 2.69866049E-4 2.19233911E-4 6.10973492E-4 0.007441556817 0.002726312428 0.003322083281 0.003326528417 1.32377E-7 1.13069562E-4 5.32929416E-4 1.49110959E-4 0.001303509558 4.25576677E-4 5.75473007E-4 0.001488164093 3.3718411E-5 2.4844826E-5 2.73454078E-4 2.17694872E-4 6.77444988E-4 2.13681013E-4 6.77046E-6 4.16332906E-4 2.7772077E-5 2.79643862E-4 1.9161576E-5 3.12781573E-4 0.015019506931 0.003516918098 0.004979275715 0.008956552775 1.54598981E-4 4.64019028E-4 2.30409809E-4 5.74885587E-4 5.46619204E-4 3.92332573E-4 3.62024508E-4 5.60900126E-4 1.13784603E-4 2.2095087E-5 2.28245948E-4 6.711134E-5 3.52013353E-4 1.33816879E-4 1.7844166E-4 1.22832046E-4 4.4838514E-5 1.4520103E-5 4.0665889E-5 2.68429459E-4 0.006390807876 0.001995816784 3.03020977E-4 0.003265818188 1.1499421E-4 2.9386206E-4 8.8721344E-5 2.15755466E-4 4.673852E-6 1.86065927E-4 4.95044E-7 5.78598104E-4 9.5552392E-5 4.7002381E-5 1.09925703E-4 8.647718E-5 0.015127427519 0.006532492454 0.001656455398 0.012394610714 0.037241853149 1.9413411E-5 0.001625465457 0.066879899684 0.419941399594 0.081972496144 0.047720186368 0.228674918306 8.0752486E-5 4.802898E-6 6.6605043E-5 2.76315891E-4 1.01932352E-4 4.70343659E-4 1.92354203E-4 5.85330275E-4 7.173695E-5 1.4281456E-5 2.06615168E-4 2.3598204E-5]
                                [0.022172306775 0.017871675353 0.002138339617 0.01626514559 0.024002615202 0.012160303668 0.005275419458 0.023277656342 0.017872170732 0.008896034323 0.00357270835 0.014797612209 0.008428563566 0.00737309667 0.00735730002 0.008753938991 0.013035709432 0.00918628969 0.011716920077 0.016978870798 0.002351368076 0.001464231129 0.009053777642 0.007030976419 0.018781725573 0.015760457842 0.001963389846 0.014722861126 0.016832571989 0.013531441527 0.004176457524 0.024046390424 0.014395059491 0.008544781192 0.00351846584 0.016075545712 0.00757132026 0.012725463528 0.011508561865 0.016456177696 0.007895012611 0.014431170362 0.008422972144 0.011932430465 0.001974181146 0.005824295545 0.010464654525 0.008724387302 0.009696539676 0.010843341116 9.29140538E-4 0.012215382639 0.011916844569 0.009823653397 0.00167105425 0.017914334306 0.016127273332 0.008208632437 0.001212922866 0.010611649208 0.004434782559 0.005615432411 0.008070136472 0.008679121744 0.006850055574 0.006260749014 0.006098762993 0.007509343859 0.004144488018 0.004501985313 0.016391452608 0.007067236643 0.011653224909 0.016606775011 0.001357239439 0.016328075975 0.016041405438 0.020149920428 0.002527910936 0.032673640762 0.008880209061 0.013529572798 0.001705408858 0.010304416928 0.007132681354 0.009102797827 0.006565863041 0.014712135403 0.009115495077 0.010953790932 0.006113033219 0.010774336259 0.005427184241 0.006160250423 0.011076526261 0.013000984209]
                                [0.0365 0.0309 0.0183 0.0243 0.0097 0.0054 0.0031 0.0054 0.012 0.0075 0.0028 0.0059 0.0048 0.0039 0.01 0.003 0.0084 0.002 0.0081 0.0036 0.0 2.0E-4 0.0015 2.0E-4 0.0461 0.0614 0.0088 0.0432 0.0105 0.0097 0.0063 0.0094 0.021 0.0144 0.0076 0.0201 0.0075 0.0111 0.0342 0.0115 0.0052 0.0026 0.01 0.0054 0.0 0.0013 0.0046 0.0012 0.0376 0.0399 0.0227 0.0258 0.007 0.0091 0.0062 0.006 0.0087 0.008 0.0023 0.0082 0.0069 0.0052 0.0133 0.0045 0.0061 0.0016 0.0042 0.0024 0.0 0.0 0.0018 2.0E-4 0.033 0.0538 0.0104 0.037 0.0032 0.0105 0.0031 0.005 0.0035 0.007 0.0011 0.0077 0.0045 0.0046 0.0082 0.0045 0.0028 0.0016 0.0036 0.0022 0.0 3.0E-4 0.003 0.0011]
                                [0.014941547714 0.008960917997 0.002207846012 0.009206905292 0.011671021651 0.007292088583 0.002303839151 0.011696245747 0.021839187574 0.012756105638 0.016760177116 0.016477954692 0.008902904323 0.007399202496 0.011508051457 0.011193623195 0.035366735114 0.013771102129 0.028449051657 0.027302974158 0.003462151975 0.002246592659 0.005490216837 0.003821226712 0.00967490433 0.004952300642 0.002800627307 0.011013465824 0.007537523174 0.007633226665 0.002613760399 0.009417097876 0.022769209355 0.017509476305 0.012862237589 0.020429513385 0.005043163511 0.006208843676 0.01050756897 0.009784640746 0.014238629786 0.01248950044 0.018741631229 0.019054082944 0.002041648941 0.003478502118 0.007147456226 0.011486811509 0.011892168979 0.009247857495 0.002809188466 0.010301267456 0.005559423089 0.005388801221 0.001100589633 0.006041247837 0.020038258645 0.018022312238 0.013193817648 0.019502915335 0.006740387717 0.00402159648 0.006814352288 0.005101032909 0.016294982792 0.009574560593 0.014103929348 0.015666874692 0.001627664547 3.27734937E-4 0.005948879802 0.003307466591 0.014774016248 0.012043464964 0.003902362388 0.018243395705 0.002681056961 0.007924048307 0.001319075649 0.006644595708 0.010997560324 0.020644964803 0.007534489574 0.011787461775 0.009205637343 0.006835344247 0.007144208769 0.010240850413 0.017059640976 0.014195709147 0.012596501722 0.017375139145 0.00520287442 0.005131607914 0.0060552541 0.013369935834]
                                [0.0017 0.0028 5.0E-4 0.0019 0.0013 0.0012 0.0 0.0018 0.0312 0.0163 0.0908 0.0149 6.0E-4 0.0033 0.0 0.0053 0.0075 0.0056 0.0217 0.0023 0.0 0.0017 7.0E-4 0.0029 0.0101 0.0241 0.0091 0.0571 0.0 0.0 0.0 2.0E-4 0.0085 0.0099 0.0901 0.0087 1.0E-4 0.0026 8.0E-4 0.0011 0.0062 0.004 0.027 0.0033 1.0E-4 0.004 0.005 0.0086 0.0024 0.0058 0.0021 0.0087 0.0 0.003 0.0 0.0017 0.0653 0.0773 0.1339 0.0524 0.0 0.0028 6.0E-4 0.0021 0.0122 0.0059 0.0115 0.0042 0.0 0.0016 0.001 0.0035 0.0017 0.0029 0.0011 0.0058 0.0 2.0E-4 0.0 1.0E-4 0.0074 0.0067 0.0391 0.0047 2.0E-4 8.0E-4 0.0 7.0E-4 0.0059 0.0035 0.0106 0.0029 9.0E-4 0.0019 0.0011 0.0072]
                                [4.0E-4 5.0E-4 0.0 4.0E-4 0.0 0.0 0.0 1.0E-4 0.0 0.0197 1.0E-4 0.0043 0.001 8.0E-4 9.0E-4 0.0035 5.0E-4 1.0E-4 7.0E-4 0.0011 0.0 0.0 9.0E-4 0.0 0.0012 6.0E-4 0.0 0.0013 1.0E-4 4.0E-4 6.0E-4 3.0E-4 0.0754 0.1007 0.0208 0.0788 0.0 0.0014 7.0E-4 0.0018 0.001 8.0E-4 0.001 0.0045 0.0 8.0E-4 9.0E-4 0.0013 3.0E-4 1.0E-4 0.0 1.0E-4 0.0 4.0E-4 0.0 0.0 0.0 0.021 2.0E-4 0.0161 0.0 0.0 6.0E-4 0.001 2.0E-4 1.0E-4 3.0E-4 0.0081 0.0 0.0 0.0017 9.0E-4 0.001 0.002 2.0E-4 0.0013 2.0E-4 0.001 3.0E-4 7.0E-4 0.1202 0.2887 0.0992 0.0844 0.001 0.0015 0.001 0.005 0.0023 0.0018 0.0019 0.0024 0.0 0.001 0.001 0.0014]
                                [0.036718003777 0.033245722246 0.002525311256 0.033598549516 0.008356844806 0.004306380304 5.84415303E-4 0.008634906912 0.018066687156 0.005650015029 0.019265105465 0.020805967497 0.013365082811 0.012431202077 0.014036657067 0.024109916596 0.016206510101 0.00778780282 0.009852583553 0.021829463826 0.004219719284 7.93671329E-4 0.006865800374 0.002939922259 0.03172375659 0.025505407135 0.001159624303 0.028791172971 0.006619077216 0.006077702895 6.56033433E-4 0.007805026957 0.004865810021 0.003980150431 0.008338639182 0.018843627434 0.012024217522 0.017881092423 0.016356774533 0.026247801917 0.005173842417 0.010936893768 0.005657773602 0.011120389322 0.002424050552 0.002096210616 0.006604046294 0.004866713859 0.023682328869 0.015821896445 8.50913754E-4 0.021061235802 0.003724567946 0.003046596953 3.21212793E-4 0.005775663598 0.002261262686 0.001616649187 0.0116067116 0.006284928996 0.008139178608 0.007910080318 0.009057263056 0.017812238647 0.005720778549 0.005181051502 0.005040707471 0.008488220258 9.74578665E-4 5.24821555E-4 0.006087753467 0.005427433785 0.027032383039 0.01808977329 0.001694875197 0.038141330868 0.004117878837 0.003799649592 2.5653018E-5 0.006247553738 0.00718066854 0.004061032938 0.00553512152 0.012209070497 0.015139996236 0.012093554606 0.00836254659 0.027653296254 0.004901072379 0.006081011299 0.001712484699 0.01000344183 0.001743221362 0.002549838329 0.006030395185 0.007223998893]
                                [0.012 0.0067 5.0E-4 0.0068 0.0048 0.0023 0.0 0.0038 0.0093 0.0056 0.0125 0.0076 0.0121 0.0042 0.0068 0.0185 0.0252 0.0105 0.0176 0.0247 0.0335 0.0051 0.0052 0.0193 0.0098 0.0057 0.0 0.0091 0.0018 0.002 0.0 0.0039 0.0098 0.0069 0.0076 0.0097 0.0055 0.0049 0.0051 0.0069 0.0154 0.0118 0.0154 0.0275 0.0216 0.0064 0.0126 0.0509 0.0118 0.0092 0.0 0.0085 0.0011 0.0029 0.0 0.0044 0.0062 0.0069 0.0088 0.0101 0.003 0.0029 0.0028 0.0036 0.0091 0.0097 0.0094 0.0126 0.0072 6.0E-4 0.005 0.0185 0.0222 0.0043 0.0 0.0322 0.0033 0.0025 0.0 0.0049 0.005 0.0084 0.0047 0.0096 0.0215 0.0027 0.014 0.0139 0.0156 0.0137 0.0098 0.0309 0.0502 0.0081 0.0088 0.0545]
                                [7.0E-4 0.001 3.0E-4 0.0092 5.0E-4 3.0E-4 0.0 2.0E-4 0.0 0.0032 0.0126 0.0047 0.0 2.0E-4 0.0 0.0012 0.0031 0.0052 0.0026 0.004 0.0 0.004 0.0015 0.0137 0.0031 9.0E-4 7.0E-4 0.016 0.0 0.0 0.0 0.0 0.0012 0.0024 0.0109 0.0043 0.0 1.0E-4 3.0E-4 9.0E-4 5.0E-4 0.0019 0.0015 0.002 0.0022 0.0018 0.0037 0.0182 0.0014 0.0022 2.0E-4 0.0088 0.0 2.0E-4 0.0 4.0E-4 0.0 0.0134 0.027 0.0152 0.0 1.0E-4 0.0 4.0E-4 0.0064 0.0101 0.0054 0.0124 0.0 0.002 9.0E-4 0.003 0.0374 0.0103 0.0031 0.3083 0.0 0.0 0.0 1.0E-4 0.0037 0.0211 0.2141 0.0392 0.0 8.0E-4 0.0 0.0028 0.003 0.0097 0.0065 0.0099 0.005 0.0092 0.0022 0.0633]
                                [2.0E-4 0.001 0.0 2.0E-4 7.0E-4 3.0E-4 0.0 9.0E-4 0.0225 0.1099 0.0072 0.0639 2.0E-4 1.0E-4 7.0E-4 3.0E-4 6.0E-4 0.0015 8.0E-4 3.0E-4 0.0 0.0 1.0E-4 0.0 7.0E-4 0.0017 0.001 0.0014 0.0 2.0E-4 8.0E-4 2.0E-4 0.031 0.1518 0.0135 0.0816 4.0E-4 0.0011 6.0E-4 4.0E-4 1.0E-4 4.0E-4 1.0E-4 9.0E-4 0.0 1.0E-4 0.0011 9.0E-4 4.0E-4 0.001 0.0 6.0E-4 0.0 2.0E-4 0.0 0.0 0.0261 0.0975 0.009 0.0522 3.0E-4 3.0E-4 4.0E-4 1.0E-4 0.001 0.0016 0.0019 0.0023 0.0 0.0 0.001 0.0 9.0E-4 0.0025 1.0E-4 4.0E-4 1.0E-4 0.0011 3.0E-4 8.0E-4 0.0302 0.1589 0.008 0.0954 3.0E-4 0.0 0.0 8.0E-4 2.0E-4 9.0E-4 7.0E-4 5.0E-4 0.0 0.0 3.0E-4 3.0E-4]
                                [0.0077 0.0047 0.0017 0.0046 0.0031 0.0015 0.0 0.0025 0.0121 0.0054 0.0019 0.0067 0.0058 0.0065 0.0046 0.006 0.0466 0.0317 0.0487 0.025 0.0012 0.001 0.0018 0.003 0.0135 0.0112 0.0028 0.0071 0.0019 0.0019 0.0 0.0019 0.0187 0.0094 0.0046 0.0205 0.0017 0.0086 0.0031 0.0054 0.0312 0.0385 0.0415 0.0324 0.0013 0.0049 0.0045 0.0063 0.0062 0.0056 0.0015 0.0027 0.0012 0.0011 0.0 9.0E-4 0.0124 0.0092 0.0023 0.0115 0.0015 0.0039 0.0019 0.0031 0.0573 0.0274 0.041 0.0405 0.0 4.0E-4 0.0011 0.0032 0.0066 0.0099 0.0019 0.0049 0.0024 0.0027 0.0 0.0015 0.0138 0.0095 0.001 0.009 0.0057 0.0132 0.0037 0.0094 0.05 0.0342 0.032 0.0371 0.0019 0.0027 0.0011 0.0032]
                                [3.34757187E-4 6.48736139E-4 3.8144594E-5 8.4665846E-4 0.003775165298 9.20824828E-4 1.9890489E-5 0.003860632488 0.001480180764 4.0508707E-5 3.16594671E-4 0.0 0.001243618346 0.001060001878 1.0255436E-4 9.71812099E-4 5.49454166E-4 1.45506661E-4 2.79178024E-4 2.67461432E-4 3.8619979E-4 1.12383214E-4 3.6000696E-5 1.92493754E-4 0.001710089561 0.001159256581 2.44166533E-4 0.001256768244 0.005333651375 9.12303471E-4 2.85467331E-4 0.006383097568 0.003647278451 0.001004229295 1.29202148E-4 6.1176997E-4 2.85158991E-4 2.22287328E-4 2.86951828E-4 8.00446E-7 4.2751466E-5 4.3307542E-5 5.4272534E-5 7.63944E-7 1.5094208E-5 1.4762484E-4 4.59873E-5 4.63714722E-4 1.32109643E-4 7.54244011E-4 9.6820775E-5 5.51103672E-4 0.001349419584 7.88807743E-4 8.1791214E-5 0.002680123244 0.002934137577 0.002515171146 0.00320526455 0.001687730545 1.37960405E-4 2.52867405E-4 1.53517461E-4 1.22745552E-4 0.001054081222 5.29899747E-4 6.30095718E-4 6.76187888E-4 1.8489857E-5 9.3373513E-5 1.0579194E-5 5.3741207E-5 0.048166047937 0.017329557089 0.002293157306 0.018634581512 0.280234946548 0.063885286587 0.009528486008 0.330225481745 0.113842093163 0.015024839001 0.006102097956 0.028140661233 4.03261316E-4 7.71813274E-4 2.45194218E-4 1.60345975E-4 7.84056118E-4 1.32974888E-4 3.7402404E-5 1.79702846E-4 5.46581847E-4 2.35347085E-4 4.79022E-7 6.70588327E-4]
                                [1.0E-4 0.0042 5.0E-4 0.0296 1.0E-4 0.0 0.0 1.0E-4 0.0293 0.0321 0.0496 0.0277 0.0 8.0E-4 0.0 9.0E-4 0.0026 0.0046 0.0035 0.0025 0.0 0.002 7.0E-4 0.0056 0.0056 0.0102 9.0E-4 0.1257 0.0 0.0 0.0 0.0 0.006 0.0167 0.0293 0.0186 0.0 0.0034 2.0E-4 0.002 6.0E-4 0.0053 0.0016 0.0027 0.0 0.002 5.0E-4 0.0063 0.0018 0.0114 0.0011 0.0736 2.0E-4 1.0E-4 0.0 3.0E-4 0.0557 0.0618 0.0978 0.0555 0.0 0.0025 0.0 9.0E-4 0.0078 0.0127 0.0082 0.0086 0.0 0.0019 0.0012 0.0038 5.0E-4 0.0166 0.0 0.0516 0.0 0.0 0.0 1.0E-4 0.0092 0.0283 0.0094 0.0229 0.0 0.0016 0.0 0.0012 0.0018 0.0056 0.0034 0.0026 0.0 0.0015 2.0E-4 0.0025]
                                [0.0013 0.004 0.0 0.0057 0.0011 1.0E-4 6.0E-4 0.001 0.0117 0.0169 0.0309 0.0066 0.0021 0.009 9.0E-4 0.0079 0.0115 0.0073 0.0073 0.0063 0.0 6.0E-4 0.0012 0.0029 0.0106 0.0084 0.0015 0.0228 9.0E-4 3.0E-4 4.0E-4 0.0017 0.0051 0.0088 0.0155 0.0096 0.0016 0.0043 0.0027 7.0E-4 0.0032 0.005 0.0128 0.0035 0.0 0.0019 7.0E-4 0.0045 0.0024 0.0099 0.0013 0.0309 0.0022 5.0E-4 6.0E-4 0.0018 0.0476 0.1345 0.1829 0.08 0.0 0.0038 0.0026 0.0043 0.0195 0.0499 0.0154 0.0094 0.0 0.0022 0.0037 0.019 0.0057 0.0062 0.0025 0.0145 9.0E-4 0.0026 0.0 0.0015 0.0112 0.0156 0.0104 0.0076 8.0E-4 0.0019 6.0E-4 3.0E-4 0.0068 0.0099 0.0039 0.0047 0.0 4.0E-4 9.0E-4 0.0033]
                                [0.0161 0.0097 0.0022 0.0088 0.0048 0.0024 0.0 0.0073 0.0135 0.0076 0.0035 0.0101 0.0081 0.0073 0.008 0.0084 0.0717 0.0171 0.0348 0.0497 0.0052 0.0042 0.0061 0.0076 0.0159 0.01 0.0022 0.0084 0.004 0.005 0.0028 0.008 0.021 0.0098 0.0016 0.0211 0.0054 0.0128 0.013 0.0096 0.0146 0.0194 0.019 0.0263 2.0E-4 0.0082 0.0067 0.0186 0.0096 0.0094 0.0036 0.0063 0.0031 0.0032 0.0 0.0029 0.0125 0.0092 9.0E-4 0.0116 0.003 0.004 0.0051 0.0033 0.0161 0.0099 0.0148 0.0142 0.0 0.0032 8.0E-4 0.0044 0.0122 0.0145 0.004 0.0157 0.005 0.0064 0.0 0.0169 0.0172 0.0131 0.0036 0.0193 0.0085 0.0087 0.0063 0.008 0.0246 0.0183 0.0165 0.0174 0.0068 0.0069 0.0049 0.0163]
                                [0.001832019172 3.42235622E-4 1.576225E-6 0.003179664772 0.001661704195 0.001626898399 2.5801897E-5 0.001328507581 0.009350488403 0.004224289013 0.009229213313 0.005735961173 6.1300245E-4 0.001878587472 0.001087349685 0.002045493651 0.002697314025 0.008078150072 0.004432870698 0.003140370209 5.3139249E-5 0.006468661271 0.003481549012 0.030926375169 0.001032430218 4.21880127E-4 2.97462769E-4 3.1479429E-5 0.001924978751 7.09367774E-4 3.517869E-6 9.53776334E-4 0.021265205902 0.003847235312 0.013176112314 0.009648025316 3.0079241E-5 0.010223747696 0.004467036232 0.031244883388 0.007426999646 0.039657001207 0.028609696263 0.101869657701 9.1469787E-5 0.019814293551 0.013449837633 0.261456614079 0.006535404875 0.001293803953 0.001283663378 0.003103766672 0.001296324191 2.60760911E-4 4.377552E-6 0.001820392786 0.002068754725 3.25483399E-4 0.007511101358 9.25697167E-4 8.545689E-6 0.002573466959 0.003538822593 0.003879009961 1.1401335E-5 0.009657713318 0.004654129131 0.008149234601 3.9193821E-5 0.009078461515 0.004782775461 0.063497756582 0.001824558257 0.001286518338 0.002172941968 0.005012316394 3.92684657E-4 6.55602418E-4 1.3641541E-5 0.002852380395 0.016100784572 0.016779984064 0.016272095596 0.01982741755 6.98812311E-4 0.002579358604 2.1133639E-5 0.003883130589 0.001708586711 0.011074152072 0.005932002763 0.007208218396 1.33410642E-4 0.009613345245 0.004522462318 0.05804040776]
                                [0.050536418625 0.010939824789 0.002288072697 0.019424091364 0.00151688758 0.002498784527 0.002614508983 0.00398301117 0.003825623912 0.002465214261 0.008463956886 0.005605739927 0.003037210804 8.11167214E-4 0.004503207138 0.002736523185 0.002933053644 0.001513284763 0.003329668955 0.005491317771 3.86138197E-4 0.00125498208 0.002369216073 3.38602881E-4 0.088768108819 0.020641390557 0.01717840248 0.037676958929 4.58463822E-4 0.00365422833 0.005405774245 0.00551960482 0.009767511475 0.005654828462 0.016183222964 0.005153318613 4.12346353E-4 0.00249141131 0.005523045713 0.003313713273 1.9216932E-4 0.003050192581 0.002596464193 0.001310028744 6.1002548E-5 0.002015696913 0.005311967254 0.002341629178 0.12872415808 0.016092878663 0.009271416762 0.072017070126 9.23879703E-4 6.48647922E-4 5.42839968E-4 0.001376724888 0.002703068727 0.004199307536 0.011897360825 0.001913380905 0.001875434081 0.001510309757 0.001505368776 0.003611025202 5.29126468E-4 0.002279130829 0.005871056581 0.004771565739 0.001341532464 9.20431E-7 0.009694999981 0.002890553519 0.067080643529 0.041869009693 0.01516237929 0.117877846666 0.001802969051 0.007863006794 0.001513223334 0.003567102065 0.014923391701 0.00996274431 0.011549715126 0.005398013994 0.004663278043 0.002578155543 0.002016738361 0.002818679447 3.02303565E-4 0.006260791115 0.001541265329 0.001357797089 3.6318404E-5 0.00323383799 7.54601773E-4 0.002126441532]
                                [0.0107 0.0074 5.0E-4 0.0074 0.0058 0.0019 0.0 0.0072 0.0221 0.0203 0.0368 0.0266 0.0011 0.0053 0.0045 0.0033 0.0145 0.0048 0.0135 0.016 0.0014 0.0 0.001 0.0017 0.0112 0.0159 0.0018 0.0096 2.0E-4 0.0028 2.0E-4 8.0E-4 0.0438 0.0753 0.0238 0.1159 0.0043 0.0053 0.0021 0.0022 0.0066 0.007 0.004 0.0053 0.0 0.0011 0.0013 0.0019 0.0032 0.0031 0.0 0.0072 0.002 0.0 0.0 0.0 0.0374 0.0614 0.0278 0.0917 0.0026 0.0 0.0016 0.0052 0.0039 0.003 0.006 0.0099 0.0 0.0 0.0043 0.0025 0.0051 0.0063 0.0018 0.0087 5.0E-4 0.0011 0.0 0.0105 0.0218 0.033 0.009 0.0267 0.0039 0.0048 0.0052 0.0085 0.0052 0.002 0.0057 0.0038 0.0032 0.0018 0.0011 0.0013]
                                [0.001179961596 0.002211505148 1.61691E-7 0.003008009987 6.9704107E-4 0.002059310006 1.2734958E-5 8.48587116E-4 0.032858047272 0.022196046913 0.034572014703 0.018819559611 5.74401358E-4 0.001971462059 1.90586616E-4 0.007421103528 0.016395062964 0.019251793639 0.054784918009 0.007341269916 1.765291E-6 3.81387913E-4 6.58758087E-4 5.32125222E-4 0.017377110647 0.036502462984 0.012482587542 0.103401226249 1.331626E-5 5.48144708E-4 0.0 1.20730556E-4 0.011145185306 0.018152746866 0.044384942487 0.013847136214 4.5042364E-5 0.004389055645 0.001127835842 0.002385541321 0.014867696528 0.018356161025 0.074150562563 0.011001118253 2.3264084E-5 0.008476494525 0.015383776115 0.002185394659 0.001116123789 0.003271428605 0.003751612508 0.01293719445 0.001339769321 0.001811151475 6.37538E-7 0.001238264182 0.056793947985 0.05442985727 0.004592688609 0.049580863753 1.63129836E-4 0.003799179503 8.283337E-4 0.001900746587 0.002469317074 0.001565632906 0.027341755021 0.001498264796 3.579149E-6 0.002830575073 0.002733219405 0.003558558565 6.88299692E-4 0.00405822906 0.001001102231 0.012229104507 2.4298469E-5 1.5199855E-4 0.0 6.83059976E-4 0.01071032207 0.00817550977 0.023163899202 0.009540087238 9.24547968E-4 7.08206814E-4 9.7003127E-5 0.00231348906 0.005734229562 0.011234254668 0.023767611345 0.005395186301 2.69147E-7 3.77234437E-4 5.15421599E-4 6.15656691E-4]
                                [1.0E-4 7.0E-4 0.0 6.0E-4 5.0E-4 8.0E-4 0.0 0.0018 0.0051 0.0062 0.0256 0.003 0.0083 0.0292 0.0027 0.0043 0.0265 0.0165 0.0224 0.0189 0.0 0.0031 0.0016 0.0018 0.002 0.0014 0.0027 0.0056 1.0E-4 0.0 0.0 3.0E-4 0.0034 0.0091 0.0318 0.004 0.0 0.0037 0.0025 0.001 0.0219 0.01 0.0211 0.0204 0.0 5.0E-4 4.0E-4 0.0032 1.0E-4 0.0038 3.0E-4 0.0023 4.0E-4 0.0042 0.0 4.0E-4 0.0163 0.0392 0.0019 0.0166 0.0 0.0142 0.0031 0.0041 0.1426 0.0665 0.0884 0.1003 0.0 6.0E-4 4.0E-4 8.0E-4 1.0E-4 5.0E-4 0.0 4.0E-4 2.0E-4 3.0E-4 0.0 3.0E-4 0.0064 0.0086 0.0126 0.0064 0.0027 0.0045 0.0 8.0E-4 0.0389 0.0313 0.0249 0.0274 0.0 0.0018 3.0E-4 3.0E-4]
                                [0.001504070375 0.002451011015 0.0 9.22452518E-4 5.2764404E-4 0.0 0.0 2.99454735E-4 0.003113389695 0.00152486029 0.003625641201 0.0 0.049619229712 0.011666740464 0.067915454213 0.01459991758 0.016839930949 0.001108262382 0.004913616405 0.002630523933 0.001651998615 0.0 0.001086872758 0.0 0.004549692944 0.003764473854 9.00163331E-4 0.004439846231 5.82017663E-4 8.25299647E-4 3.82114636E-4 0.001760645911 0.007731349522 0.007110850526 0.005502030965 0.004169777241 0.086898545516 0.053706846809 0.213220866558 0.061827507476 0.004027346334 0.001352443929 0.002749666137 0.002363353537 0.001120356515 0.001064583571 0.004188568126 0.001629409573 0.001298370161 0.001809622152 3.33838055E-4 5.19148161E-4 0.0 0.0 2.14695851E-4 5.38438803E-4 0.001915370854 0.003229133547 0.003300598879 0.00111405957 0.053709145693 0.009489396694 0.050293902428 0.010601457233 0.004217454113 0.0 0.001474884534 0.001561042738 0.0 0.0 0.001877589181 0.0 0.004051634552 0.002739770937 5.1315107E-4 0.003605650899 2.25490615E-4 6.2869502E-4 3.95807994E-4 2.57375147E-4 0.002016721688 0.001990834246 0.001498473091 0.001182226502 0.040810502825 0.025570395801 0.069845218085 0.023579061797 0.00480366974 9.12157512E-4 0.00178173568 0.0 0.001651698761 0.0 0.002572751958 0.0]
                                [4.53360683E-4 3.66800484E-4 0.0 0.0 0.0 0.0 0.0 0.0 0.019767154372 0.081880579925 0.009248715085 0.033209652209 0.0 0.0 2.37418521E-4 0.0 0.00106844882 2.21573159E-4 4.98801514E-4 3.13240541E-4 0.0 0.0 0.0 0.0 1.64739382E-4 7.36874802E-4 1.63953661E-4 7.22731835E-4 1.99049174E-4 2.83776026E-4 0.0 1.95775338E-4 0.060572627805 0.186375634076 0.015149082593 0.156015396977 5.94528453E-4 0.0 1.731204E-4 4.57813099E-4 0.0 0.0 3.20704886E-4 0.0 0.0 1.90144342E-4 0.0 2.1083498E-4 3.49907495E-4 5.68337772E-4 1.49417834E-4 0.0 0.0 1.37501074E-4 0.0 0.0 0.053282058965 0.162918343743 0.013046494744 0.107590399754 0.0 1.83203812E-4 0.0 0.0 2.51168628E-4 5.38218489E-4 0.0 9.95769681E-4 0.0 0.0 1.87132414E-4 0.0 0.002071289985 6.96410201E-4 9.9393633E-5 2.07430191E-4 2.30608944E-4 1.65787009E-4 0.0 4.38955809E-4 0.014558613696 0.043510446939 0.002752640544 0.024328785234 0.0 0.0 2.31525618E-4 0.0 0.0 2.30608944E-4 2.39513775E-4 0.0 0.0 0.0 2.47501933E-4 0.0]
                                [0.028645992536 0.020214638433 0.020478996485 0.024600145425 0.011993038821 0.008425119792 0.0 0.003881383185 0.006315518136 0.003844326917 0.003110664425 0.003420418673 0.001166747126 1.5173057E-5 0.006332455573 4.967216E-6 0.004726009142 6.27949341E-4 0.002723758627 0.00712236847 0.0 0.001463950688 8.4761253E-4 0.001075455294 0.063559283793 0.033757004716 0.022428985806 0.020086515438 0.013476821609 0.016105687737 7.97471998E-4 0.010388665882 0.006035447394 0.0 0.002924419074 0.003971201575 0.002766634838 0.002694462454 0.006911164721 0.002915200165 0.001848876535 0.004453689857 0.0 0.001597389007 0.0 0.002185437348 1.13909674E-4 0.002533718333 0.054676448679 0.109945988997 0.04652486901 0.050428709482 0.004945060708 0.009369875774 0.00562572654 0.003055770064 0.023679051295 0.01591920184 0.012648077606 0.018129778378 0.002225127336 0.004853149157 1.95758628E-4 0.0 0.00126974984 0.006710339996 2.77684976E-4 0.004238477669 0.0 0.001073632302 0.002924696996 8.25098263E-4 0.031451263182 0.062538293699 0.012802590728 0.030297679613 0.010220857436 0.007578365252 0.001592714217 0.00610072719 0.026571894346 0.012055691082 0.00536335968 0.00553249918 0.0 0.007994129155 0.011202143591 2.70578768E-4 0.001682291048 0.002344527656 4.81274164E-4 3.58148002E-4 0.0 0.0 0.00136050485 6.9515778E-5]
                                [0.009896768001 0.00699892877 0.001448442998 0.004966565004 0.008032567646 0.001635970413 0.0 0.003428449701 0.020987618053 0.013141283697 0.019659894285 0.005728519312 0.020430527125 0.008892560661 0.00624194848 0.014850877622 0.020663282773 0.009502478424 0.019911492456 0.014488390255 0.005281679682 0.0 0.007136421644 0.0 0.014832947875 0.007822175302 0.001276976732 0.012563654719 0.00720079041 0.004598473117 8.23709283E-4 1.96360325E-4 0.014333994691 0.005936643659 0.011559391627 0.013632596885 0.026296323089 0.015799883085 0.062860606381 0.036622351112 0.009028014894 0.01077163242 0.01335066131 0.008961029966 0.0 0.002886454337 0.011769492499 0.008550807482 0.013465295053 0.006468298058 6.58103089E-4 0.010473080347 0.004169655524 0.002392804944 4.18193916E-4 0.001994012217 0.010038112013 0.006953875155 0.010468363027 0.009823820735 0.036217800837 0.005847109647 0.038241179315 0.020167954419 0.017489823879 0.004045612446 0.013402246274 0.014159693736 0.005546226924 0.0 0.008685244444 0.002768571683 0.006938146585 0.0102466074 0.0 0.024625787226 0.009798724567 0.00326650715 0.002057436753 0.01640091685 0.0110775208 0.00644878754 0.014083416181 0.012418585885 0.021830329049 0.003405686728 0.010191520401 0.014527539313 0.01451505009 0.007406083457 0.004892531217 0.017014780759 0.002081479927 5.78978775E-4 0.009429195885 0.007869671577]
                                [0.00203977291 0.001487162284 2.83945614E-4 5.97865638E-4 0.001272881297 0.001528194862 3.07246308E-4 0.002498251826 0.005907232039 0.010626457816 0.019930323239 0.011334530552 0.004459339879 0.012822242916 0.001172344272 0.003993137549 0.055028652133 0.027594604208 0.05179104405 0.039072244419 8.0653426E-5 1.6317121E-4 0.001255079507 0.001850383492 0.003705850083 0.003980723373 8.1174197E-4 0.019038431345 0.001279106069 0.001214977303 2.07832588E-4 0.002297026211 0.006594444885 0.006510631971 0.011904935324 0.006238556327 0.003561422681 0.003901610981 0.00239030418 0.001635922667 0.037889377691 0.021740734953 0.047240103343 0.020740621431 9.539626E-5 0.0039194551 0.005497357314 0.006788718691 0.001375311846 0.001961639088 1.3229664E-5 0.001934996293 0.001320607475 0.001845501071 2.05060777E-4 0.001225532856 0.009606515027 0.01950742672 0.022502717383 0.017307472184 0.002242886513 0.005207287095 0.001358121778 0.002513112942 0.098052824719 0.040226363963 0.044620700983 0.05552806828 3.7393232E-5 0.002460704995 8.17201563E-4 0.007833561271 0.002680062424 0.002031643866 2.650717E-4 0.003017187457 0.004201896751 0.002807761534 6.197E-9 0.008019382986 0.011303288768 0.010807526975 0.010364329328 0.00724914536 6.28145256E-4 0.005073753861 2.0138391E-5 0.00123553847 0.028789680542 0.036639492442 0.01914369428 0.023071688831 8.534935E-6 0.002718509845 0.001369161173 0.002568076726]
                                [0.005205626909 0.004738227415 7.82697937E-4 0.002718242455 0.001324163246 0.001771084129 0.0 0.001322719819 0.013723285787 0.005424306001 0.006706429454 0.004475614034 0.143076389009 0.001464085387 0.001226280897 0.002535197951 0.005047301081 0.001845691228 0.005688813879 0.002592754577 0.001136337392 9.5897638E-4 6.766061E-5 2.56027748E-4 0.005065073268 0.002234153344 2.66312161E-4 0.003100570009 0.004730649426 0.001261644842 3.60856587E-4 2.6802623E-4 0.017438484778 0.007144870207 0.007503561655 0.007148027702 0.330601017471 0.002813598808 0.004396857083 0.005430891633 0.004899079238 0.005724448467 0.005661478992 0.003900589062 0.0 3.81244984E-4 4.03257236E-4 0.001439096069 0.010755871858 0.012442335117 0.001629628347 0.007412355152 0.001418166387 0.001346716782 0.0 0.004560685974 0.014215313743 0.020143646182 0.009368468496 0.00758962595 0.10985187198 2.3455678E-5 0.001565937159 0.0 0.004777380354 0.00196495433 0.001229167749 0.003747225013 0.0 5.0790565E-5 0.00474291855 0.00229847603 0.007079284522 0.006268800628 0.001446403415 0.00980627775 0.002870433721 0.003107967569 1.2810409E-5 0.002130587503 0.018757776459 0.007989184406 0.004609130971 0.011880932482 0.052813887494 4.7091785E-5 0.0 0.001929770813 0.001721015277 0.003489212553 0.001152846581 0.001527957003 0.001189022454 2.80295354E-4 0.002353055589 1.39561285E-4]
                                [0.001397438842 9.17187665E-4 0.0 5.13409971E-4 2.55194996E-4 2.68898964E-4 0.0 3.07698016E-4 0.005433792471 0.001815415488 0.005432039331 0.001629213189 9.64030891E-4 0.003365667809 0.00178575365 0.0056530891 0.004092746888 0.007880729211 0.005520833604 0.003232484 0.006364675555 0.016610905647 0.006592842245 0.086392897435 0.001168515637 3.34291811E-4 5.3651998E-5 1.86671902E-4 1.03366092E-4 1.44310792E-4 0.0 2.90999509E-4 0.004391532554 0.002647660966 0.002284563551 0.002423984267 0.002566579967 0.019610897429 0.008833600032 0.01094260982 0.020738003352 0.043283797246 0.037088261728 0.005052716018 0.009589841941 0.033913223722 0.018516481606 0.118967102954 0.002136629077 4.29208379E-4 0.0 0.001318507256 8.1591411E-5 1.05219031E-4 0.0 1.59679354E-4 0.003384240869 0.001687414029 0.00218930896 0.001043993631 5.51460783E-4 0.003163987597 0.002152847013 0.002007733244 0.003655048127 0.007071175327 0.00823223844 4.0788451E-4 0.0 0.0 0.004217808299 0.031648997321 7.75902768E-4 8.29292467E-4 0.0 0.001998073667 5.21370368E-4 6.6732589E-4 0.0 0.001245779535 0.007622257672 0.005116619241 0.00191802811 0.006193158626 2.03237563E-4 0.010180964868 0.003250427227 0.016492812256 0.009929552068 0.029010563206 0.015683218557 0.017291563618 0.009389658706 0.030117076984 0.012698750811 0.233659783276]
                                [0.06998198728 0.055152357207 0.017846984044 0.026804715973 0.009301971097 0.003479048871 1.54433523E-4 0.003976789927 0.005197221326 0.00897646999 0.03163899636 0.004769666594 0.0 0.003049595749 0.003909981984 0.005105527437 0.010119181163 0.006349682655 0.014659610308 0.007892413403 0.0 0.0 0.002925171448 0.0 0.051410211701 0.025825650831 0.014496183324 0.040355074099 0.002618863169 0.00111151227 0.00152004695 1.39750691E-4 0.009126471767 0.007067603037 0.019875180409 0.001634058687 0.001765447503 3.83359117E-4 0.003556845215 0.002232755492 0.0 0.002774953681 0.006902414709 0.003041424895 0.001116854814 0.0 0.002169693346 0.001143663251 0.078046610108 0.073460914669 0.017383296915 0.059602472118 2.65759858E-4 9.45012884E-4 0.0 0.004796744744 0.011638793032 0.003781751661 0.019684388045 0.005761336823 0.002930098053 0.0 5.33632401E-4 0.0 0.007090803763 0.007111327534 0.005643063554 0.011237674345 0.0 0.0 0.003640593813 0.006181023776 0.026894406511 0.031608136996 0.012500468152 0.04120717639 8.20444889E-4 4.44119287E-4 1.43297155E-4 0.001369869047 0.012076583098 0.0 0.015830196802 0.0134621373 0.001191207434 0.001682664015 0.002438756 0.0 0.011498665964 0.003584215038 0.00169429667 0.003799705772 0.0 0.0 3.53695951E-4 0.006104834136]
                                [0.0 0.0 0.0019673 0.0 0.0 0.0 0.00482 0.0 0.065119 0.054397 0.0204604 0.0219359 0.0075743 0.003738 0.0065906 0.0093447 0.0 0.0087547 0.0090498 0.0 0.0053118 0.0024592 0.0062955 0.0095416 0.0 0.0 0.0022624 0.0 0.0 0.0 0.0032461 0.0 0.0694472 0.0638403 0.0173126 0.0342318 0.0053118 0.0067873 0.0082628 0.0 0.0 0.0 0.0 0.0 0.0046233 0.0060004 0.0073775 0.0091481 0.008853 0.0093449 8.853E-4 0.0081645 0.0073775 0.0065906 0.0015739 0.0 0.0485934 0.0494787 0.0157387 0.0188865 0.003738 0.0033445 0.0050167 0.0063939 0.0 0.0065906 0.0078694 0.0084596 0.0032461 0.001869 0.0033445 0.0056069 0.0 0.0 0.0016722 0.0 0.0 0.0 0.0019673 0.0 0.0849892 0.090301 0.0151485 0.045544 0.0076726 0.0064922 0.0036396 0.0 0.0 0.0091481 0.0060004 0.0 0.0086563 0.0043282 0.0082628 0.0]])
